package com.AppFlix.i220968_i228810

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainFeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_feed)
        
        // Set status bar color to match the app theme
        window.statusBarColor = ContextCompat.getColor(this, R.color.light_gray)
        
        // Set up header interactions
        val cameraIcon = findViewById<ImageView>(R.id.cameraIcon)
        val messageIcon = findViewById<ImageView>(R.id.messageIcon)
        val shareIcon = findViewById<ImageView>(R.id.shareIcon)
        
        // Camera icon click
        cameraIcon.setOnClickListener {
            // TODO: Open camera functionality
        }
        
        // Message icon click
        messageIcon.setOnClickListener {
            // TODO: Open messages/DMs screen
        }
        
        // Share icon click
        shareIcon.setOnClickListener {
            // TODO: Open share functionality
        }
        
        // Set up post interactions
        val likeIcon = findViewById<ImageView>(R.id.likeIcon)
        val commentIcon = findViewById<ImageView>(R.id.commentIcon)
        val sharePostIcon = findViewById<ImageView>(R.id.sharePostIcon)
        val bookmarkIcon = findViewById<ImageView>(R.id.bookmarkIcon)
        val moreOptionsIcon = findViewById<ImageView>(R.id.moreOptionsIcon)
        
        var isLiked = false
        var isSaved = false
        
        // Like button functionality
        likeIcon.setOnClickListener {
            if (isLiked) {
                likeIcon.setColorFilter(ContextCompat.getColor(this, android.R.color.black))
                isLiked = false
            } else {
                likeIcon.setColorFilter(ContextCompat.getColor(this, R.color.instagram_red))
                isLiked = true
            }
        }
        
        // Comment icon click
        commentIcon.setOnClickListener {
            // TODO: Open comments screen
        }
        
        // Share post icon click
        sharePostIcon.setOnClickListener {
            // TODO: Open share post functionality
        }
        
        // Bookmark functionality
        bookmarkIcon.setOnClickListener {
            if (isSaved) {
                bookmarkIcon.setColorFilter(ContextCompat.getColor(this, android.R.color.black))
                isSaved = false
            } else {
                bookmarkIcon.setColorFilter(ContextCompat.getColor(this, R.color.brown))
                isSaved = true
            }
        }
        
        // More options click
        moreOptionsIcon.setOnClickListener {
            // TODO: Show more options menu
        }
        
        // Set up bottom navigation
        val homeNav = findViewById<TextView>(R.id.homeNav)
        val searchNav = findViewById<TextView>(R.id.searchNav)
        val reelsNav = findViewById<TextView>(R.id.reelsNav)
        val profileNav = findViewById<TextView>(R.id.profileNav)
        
        // Search navigation
        searchNav.setOnClickListener {
            // TODO: Navigate to search screen
        }
        
        // Reels navigation
        reelsNav.setOnClickListener {
            // TODO: Navigate to reels screen
        }
        
        // Profile navigation
        profileNav.setOnClickListener {
            // TODO: Navigate to profile screen
        }
        
        // Story interactions
        val yourStory = findViewById<TextView>(R.id.yourStoryLabel)
        val addStoryIcon = findViewById<ImageView>(R.id.addStoryIcon)
        
        // Add story click
        addStoryIcon.setOnClickListener {
            // TODO: Open camera to add story
        }
        
        yourStory.setOnClickListener {
            // TODO: Open camera to add story
        }
    }
}
