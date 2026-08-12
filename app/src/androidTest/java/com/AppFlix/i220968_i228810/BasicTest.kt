package com.AppFlix.i220968_i228810

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BasicTest {

    @Test
    fun testMainActivityLaunches() {
        // Simple test - just launch MainActivity and verify it doesn't crash
        ActivityScenario.launch(MainActivity::class.java)
        // Test passes if activity launches without exception
    }

    @Test
    fun testMainFeedActivityLaunches() {
        // Simple test - just launch MainFeedActivity and verify it doesn't crash
        ActivityScenario.launch(MainFeedActivity::class.java)
        // Test passes if activity launches without exception
    }
}