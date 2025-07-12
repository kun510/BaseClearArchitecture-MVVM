package com.example.basecleararchitecture.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.basecleararchitecture.ads.AppOpenAdManager
import com.example.basecleararchitecture.ads.InterstitialAdManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    @Inject
    lateinit var interstitialAdManager: InterstitialAdManager
    
    @Inject
    lateinit var appOpenAdManager: AppOpenAdManager
    
    protected lateinit var binding: T
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    
    abstract fun getViewBinding(): T
    abstract fun setupObservers()
    abstract fun setupViews()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        
        // Initialize Firebase Analytics
        firebaseAnalytics = Firebase.analytics
        
        // Initialize ads
        initializeAds()
        
        // Setup views and observers
        setupViews()
        setupObservers()
        
        // Log screen view
        logScreenView()
    }
    
    private fun initializeAds() {
        // Load interstitial ad
        interstitialAdManager.loadAd()
        
        // Load app open ad
        appOpenAdManager.loadAd(this)
    }
    
    protected fun showInterstitialAd(onAdCompleteListener: () -> Unit = {}) {
        interstitialAdManager.showAd(this, onAdCompleteListener)
    }
    
    protected fun showAppOpenAd(onShowAdCompleteListener: AppOpenAdManager.OnShowAdCompleteListener) {
        appOpenAdManager.showAdIfAvailable(this, onShowAdCompleteListener)
    }
    
    protected fun logEvent(eventName: String, params: Bundle? = null) {
        firebaseAnalytics.logEvent(eventName, params)
    }
    
    private fun logScreenView() {
        val screenName = this::class.java.simpleName
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }
}