package com.example.basecleararchitecture.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.basecleararchitecture.utils.ads.AppOpenAdManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    
    private lateinit var appOpenAdManager: AppOpenAdManager
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        firebaseAnalytics = Firebase.analytics
        appOpenAdManager = AppOpenAdManager(this)
        
        // Load the app open ad
        appOpenAdManager.loadAd(this)
        
        // Log splash screen view
        firebaseAnalytics.logEvent("splash_screen_view", null)
        
        // Show splash for 2 seconds then show ad or go to main
        lifecycleScope.launch {
            delay(2000)
            showAdAndProceed()
        }
    }
    
    private fun showAdAndProceed() {
        appOpenAdManager.showAdIfAvailable(
            this,
            object : AppOpenAdManager.OnShowAdCompleteListener {
                override fun onShowAdComplete() {
                    startMainActivity()
                }
            }
        )
    }
    
    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}