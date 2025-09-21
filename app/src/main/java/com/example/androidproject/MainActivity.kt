package com.example.androidproject

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient


class MainActivity : AppCompatActivity() {
    //private val mStompClient: StompClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "wss://publicservice-v2-uat.neml.xyz/public-service/broadcast");
        mStompClient.connect();

        mStompClient.topic("/topic/broadcast").subscribe { topicMessage ->
            Log.d("MainActivity", "mStompClient message "+ topicMessage.getPayload())
        }

        mStompClient.send("/topic/hello-msg-mapping", "My first STOMP message!").subscribe()
    }



}