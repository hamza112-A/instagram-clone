package com.AppFlix.i220968_i228810

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SimpleTest {

    @Test
    fun testAppContext() {
        // Simple context test - just verify app doesn't crash on startup
        ActivityScenario.launch(MainActivity::class.java)
        Thread.sleep(1000) // Wait 1 second
        // Test passes if no exception thrown
    }

    @Test
    fun testActivityTransition() {
        // Simple transition test
        val scenario1 = ActivityScenario.launch(MainActivity::class.java)
        scenario1.close()
        
        val scenario2 = ActivityScenario.launch(MainFeedActivity::class.java)
        scenario2.close()
        
        // Test passes if both activities can be launched and closed
    }
}