# Instagram Clone (AppFlix)

A fully featured, offline-first Instagram Clone Android application built with modern Android development practices, MVVM architecture, and real-time capabilities.

---

## 🚀 Overview

**AppFlix** is a robust social media Android application mimicking core features of Instagram. Built with a focus on reliability and offline-first capabilities, it allows users to share posts, stories, interact with comments/likes, and converse in real-time via chat and video/audio calls even with intermittent network connectivity.

---

## 🛠️ Tech Stack & Architecture

- **Core Language:** [Kotlin](https://kotlinlang.org/)
- **Architecture:** MVVM (Model-View-ViewModel) with Repository Pattern
- **UI Components:** Material Design 3, View Binding, Data Binding
- **Local Database (Offline Cache):** [Room Database](https://developer.android.com/training/data-storage/room)
- **Background Work:** [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
- **Image/Media Loading:** [Glide](https://github.com/bumptech/glide) & [Picasso](https://square.github.io/picasso/)
- **Networking:** [Retrofit 2](https://square.github.io/retrofit/) & OkHttp 4
- **Real-Time Communication:** [Agora RTC SDK](https://www.agora.io/) (Audio/Video Calling)
- **Backend Services (Firebase):**
  - **Firebase Auth:** User registration, email/password login, forgot password flows.
  - **Firebase Realtime Database:** Real-time chat messaging, user presence tracking.
  - **Firebase Storage:** Media uploads (images, posts, stories).
  - **Firebase Cloud Messaging (FCM):** Push notifications.
  - **Cloud Functions:** Serverless handlers for notifications and database maintenance.

---

## ✨ Features

### 👤 Authentication & Profiles
- **Secure Auth:** Sign up, log in, and request password resets.
- **Session Management:** Secure local storage of sessions via `SessionManager`.
- **Profile Customization:** Edit bio, update profile picture, and view individual/other user profiles with statistics (followers, following, posts counts).

### 📸 Feed & Stories
- **Interactive Feed:** Scroll posts, double-tap to like, view & write comments.
- **Stories Screen:** Upload 24-hour disappearing stories with progress indicator views.
- **Camera Integration:** Seamless capture via CameraX API.

### 💬 Real-Time Messaging & Presence
- **Chat Rooms:** Exchange text or photo messages instantly.
- **Message Controls:** Edit or delete sent messages with real-time UI updates.
- **Active Presence:** View online/offline indicators for users.
- **Notifications:** Receive instant foreground/background updates for messages and calls.

### 📞 Agora Audio/Video Calling
- **Voice/Video Calls:** High-quality, real-time calling implemented using Agora SDK.
- **Controls:** Toggle speaker, mute/unmute audio, or flip cameras during calls.
- **Call Management:** Complete foreground service to manage active call notifications and states.

---

## 📶 Offline-First Implementation (Room + WorkManager)

To ensure a smooth user experience regardless of connection state, the app employs an offline queue system:
- **Local Caching:** Feed posts, comments, stories, and chat threads are stored in the local Room Database (`AppDatabase`).
- **Pending Sync Queue:** Any user action performed offline (likes, comments, posts, or stories) is saved into specific pending tables (`PendingPostEntity`, `PendingStoryEntity`, `PendingLikeEntity`, `PendingCommentEntity`).
- **WorkManager Sync:** A persistent worker automatically detects network reconnection and fires queue syncing requests to the Firebase Backend.
