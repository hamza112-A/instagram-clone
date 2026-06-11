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
