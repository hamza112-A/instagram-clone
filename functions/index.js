/**
 * Firebase Cloud Functions for Socially App
 * 
 * Handles push notifications for:
 * - New messages
 * - Follow requests
 * - Screenshot alerts
 * 
 * Setup Instructions:
 * 1. Run: firebase init functions
 * 2. Replace this file content
 * 3. Run: firebase deploy --only functions
 */

const functions = require('firebase-functions');
const admin = require('firebase-admin');

// Initialize Firebase Admin SDK
admin.initializeApp();

/**
 * Send push notification when a new notification is created
 * Triggers on: /notifications/{userId}/{notificationId}
 */
exports.sendNotificationOnCreate = functions.database
  .ref('/notifications/{userId}/{notificationId}')
  .onCreate(async (snapshot, context) => {
    try {
      const notificationData = snapshot.val();
      const recipientUserId = context.params.userId;
      const notificationId = context.params.notificationId;

      console.log(`New notification for user ${recipientUserId}:`, notificationData);

      // Get recipient's FCM token
      const userSnapshot = await admin.database()
        .ref(`/users/${recipientUserId}/fcmToken`)
        .once('value');
      
      const fcmToken = userSnapshot.val();

      if (!fcmToken) {
        console.warn(`No FCM token found for user ${recipientUserId}`);
        return null;
      }

      // Build notification payload based on type
      let notification = {};
      let data = {
        notificationId: notificationId,
        type: notificationData.type || 'unknown',
        fromUserId: notificationData.fromUserId || '',
        timestamp: String(notificationData.timestamp || Date.now())
      };

      switch (notificationData.type) {
        case 'message':
          notification = {
            title: notificationData.senderName || 'New Message',
            body: notificationData.messageText || 'You have a new message'
          };
          data.senderId = notificationData.fromUserId;
          data.senderName = notificationData.fromUserName || notificationData.senderName;
          data.messageText = notificationData.messageText || '';
          break;

        case 'follow_request':
          notification = {
            title: 'New Follow Request',
            body: `${notificationData.fromUserName || 'Someone'} wants to follow you`
          };
          data.requesterId = notificationData.fromUserId;
          data.requesterName = notificationData.fromUserName || '';
          break;

        case 'screenshot':
          notification = {
            title: 'Screenshot Alert',
            body: `${notificationData.fromUserName || 'Someone'} took a screenshot of your conversation`
          };
          data.otherUserName = notificationData.fromUserName || '';
          break;

        case 'call':
          const callType = notificationData.callType || 'voice';
          notification = {
            title: `Incoming ${callType === 'video' ? 'Video' : 'Voice'} Call`,
            body: `${notificationData.fromUserName || 'Someone'} is calling you`
          };
          data.callType = callType;
          data.callerName = notificationData.fromUserName || '';
          break;

        default:
          notification = {
            title: 'New Notification',
            body: notificationData.message || 'You have a new notification'
          };
      }

      // Send FCM message
      const message = {
        token: fcmToken,
        notification: notification,
        data: data,
        android: {
          priority: 'high',
          notification: {
            channelId: notificationData.type === 'message' 
              ? 'messages_channel' 
              : notificationData.type === 'screenshot' 
                ? 'alerts_channel' 
                : 'social_channel',
            sound: 'default',
            priority: 'high'
          }
        }
      };

      const response = await admin.messaging().send(message);
      console.log(`Successfully sent notification to ${recipientUserId}:`, response);

      // Update notification as sent
      await snapshot.ref.update({
        sent: true,
        sentAt: admin.database.ServerValue.TIMESTAMP
      });

      return response;

    } catch (error) {
      console.error('Error sending notification:', error);
      
      // Update notification with error
      await snapshot.ref.update({
        error: error.message,
        errorAt: admin.database.ServerValue.TIMESTAMP
      });

      return null;
    }
  });

/**
 * Send push notification when a new message is created
 * Triggers on: /messages/{chatId}/{messageId}
 * 
 * This provides real-time message notifications without requiring
 * the app to create a separate notification entry
 */
exports.sendMessageNotification = functions.database
  .ref('/messages/{chatId}/{messageId}')
  .onCreate(async (snapshot, context) => {
    try {
      const message = snapshot.val();
      const chatId = context.params.chatId;

      console.log(`New message in chat ${chatId}:`, message);

      // Skip if message is from system or deleted
      if (message.senderId === 'system' || message.deleted) {
        return null;
      }

      // Extract recipient from chatId (format: userId1_userId2)
      const userIds = chatId.split('_');
      if (userIds.length !== 2) {
        console.warn(`Invalid chatId format: ${chatId}`);
        return null;
      }

      // Find recipient (user who is not the sender)
      const recipientId = userIds.find(uid => uid !== message.senderId);

      if (!recipientId) {
        console.warn(`No recipient found in chat ${chatId}`);
        return null;
      }

      // Get recipient's FCM token
      const userSnapshot = await admin.database()
        .ref(`/users/${recipientId}/fcmToken`)
        .once('value');
      
      const fcmToken = userSnapshot.val();

      if (!fcmToken) {
        console.warn(`No FCM token found for user ${recipientId}`);
        return null;
      }

      // Get sender info
      const senderSnapshot = await admin.database()
        .ref(`/users/${message.senderId}`)
        .once('value');
      
      const sender = senderSnapshot.val();
      const senderName = sender 
        ? `${sender.firstName} ${sender.lastName}` 
        : 'Someone';

      // Build message text
      let messageText = 'New message';
      if (message.type === 'text') {
        messageText = message.text || 'New message';
      } else if (message.type === 'image') {
        messageText = '📷 Sent a photo';
      } else if (message.type === 'post') {
        messageText = '📱 Shared a post';
      }

      // Send FCM notification
      const fcmMessage = {
        token: fcmToken,
        notification: {
          title: senderName,
          body: messageText
        },
        data: {
          type: 'message',
          senderId: message.senderId,
          senderName: senderName,
          messageText: messageText,
          chatId: chatId
        },
        android: {
          priority: 'high',
          notification: {
            channelId: 'messages_channel',
            sound: 'default',
            priority: 'high'
          }
        }
      };

      const response = await admin.messaging().send(fcmMessage);
      console.log(`Successfully sent message notification to ${recipientId}:`, response);

      return response;

    } catch (error) {
      console.error('Error sending message notification:', error);
      return null;
    }
  });

/**
 * Clean up old notifications (optional)
 * Runs daily to delete notifications older than 30 days
 */
exports.cleanupOldNotifications = functions.pubsub
  .schedule('every 24 hours')
  .onRun(async (context) => {
    try {
      const thirtyDaysAgo = Date.now() - (30 * 24 * 60 * 60 * 1000);
      
      const notificationsRef = admin.database().ref('/notifications');
      const snapshot = await notificationsRef.once('value');
      
      let deletedCount = 0;
      const updates = {};

      snapshot.forEach((userSnapshot) => {
        userSnapshot.forEach((notificationSnapshot) => {
          const notification = notificationSnapshot.val();
          if (notification.timestamp < thirtyDaysAgo) {
            updates[`${userSnapshot.key}/${notificationSnapshot.key}`] = null;
            deletedCount++;
          }
        });
      });

      if (deletedCount > 0) {
        await notificationsRef.update(updates);
        console.log(`Deleted ${deletedCount} old notifications`);
      } else {
        console.log('No old notifications to delete');
      }

      return null;
    } catch (error) {
      console.error('Error cleaning up notifications:', error);
      return null;
    }
  });

/**
 * Update user's online status when they disconnect (optional)
 * Provides server-side presence management
 */
exports.updatePresenceOnDisconnect = functions.database
  .ref('/presence/{userId}')
  .onDelete(async (snapshot, context) => {
    try {
      const userId = context.params.userId;
      
      // Update user's onlineStatus to offline
      await admin.database()
        .ref(`/users/${userId}/onlineStatus`)
        .set('offline');
      
      console.log(`Set user ${userId} to offline`);
      return null;
    } catch (error) {
      console.error('Error updating presence:', error);
      return null;
    }
  });
