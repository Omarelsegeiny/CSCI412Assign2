package com.example.csci412assign2

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import androidx.test.core.app.ApplicationProvider
import androidx.test.uiautomator.UiSelector
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUITest {
    private lateinit var device: UiDevice


    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Launch the app from the home screen
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        val intent = context.packageManager.getLaunchIntentForPackage("com.example.csci412assign2")
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) // Clear out any previous instances
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg("com.example.csci412assign2").depth(0)), 5000)
    }

    @Test
    fun testStartActivityExplicitly() {
        val startExplicitBtn = device.findObject(UiSelector().text("Start Activity Explicitly"))
        startExplicitBtn.click()

        // Wait for the second activity to appear
        device.wait(Until.hasObject(By.pkg("com.example.csci412assign2").depth(0)), 5000)

        val challengeText = device.findObject(UiSelector().textContains("Battery Optimization"))
        assertTrue("Challenge text not found", challengeText.exists())
    }
}