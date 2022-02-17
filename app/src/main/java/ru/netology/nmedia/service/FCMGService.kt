package ru.netology.nmedia.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson

class FCMGService : FirebaseMessagingService() {


    override fun onCreate() {
        super.onCreate()
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        println(Gson().toJson(remoteMessage))
        remoteMessage.data["action"]?.let {
            when (Action.valueOf(it)) {
                Action.LIKE -> handLike(Gson().fromJson(remoteMessage.data["content"], Like::class.java))
            }
        }

    }

    override fun onNewToken(token: String) {
        println(token)
    }

    private fun handLike(like: Like) {

    }
}

enum class Action {
    LIKE
}

data class Like(
    val userId: Int,
    val userName: String,
    val postId: Int,
    val postAuthor:String,
)