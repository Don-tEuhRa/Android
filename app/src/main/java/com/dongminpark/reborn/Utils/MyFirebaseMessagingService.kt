package com.dongminpark.reborn.Utils

import com.google.firebase.messaging.FirebaseMessagingService


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        //Log.e("FCM Log", "Refreshed token: $token")
    }
}