package com.example.basecleararchitecture.presentation.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.basecleararchitecture.presentation.ui.screens.MainScreen
import com.example.basecleararchitecture.presentation.ui.theme.BaseClearArchitectureTheme
import com.example.basecleararchitecture.presentation.viewmodel.UserViewModel
import com.example.basecleararchitecture.utils.ads.InterstitialAdManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private lateinit var interstitialAdManager: InterstitialAdManager
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        firebaseAnalytics = Firebase.analytics
        interstitialAdManager = InterstitialAdManager(this)
        
        // Load interstitial ad
        interstitialAdManager.loadAd()
        
        // Log main screen view
        firebaseAnalytics.logEvent("main_screen_view", null)
        
        setContent {
            BaseClearArchitectureTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val userViewModel: UserViewModel = hiltViewModel()
                    MainScreen(
                        viewModel = userViewModel,
                        onShowInterstitialAd = {
                            interstitialAdManager.showAd(this@MainActivity)
                        }
                    )
                }
            }
        }
    }
}