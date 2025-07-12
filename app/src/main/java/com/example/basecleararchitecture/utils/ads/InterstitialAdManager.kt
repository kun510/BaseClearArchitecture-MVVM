package com.example.basecleararchitecture.utils.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.example.basecleararchitecture.utils.Constants

class InterstitialAdManager(private val context: Context) {
    
    private var interstitialAd: InterstitialAd? = null
    private var isLoadingAd = false
    
    fun loadAd() {
        if (isLoadingAd || interstitialAd != null) {
            return
        }
        
        isLoadingAd = true
        val adRequest = AdRequest.Builder().build()
        
        InterstitialAd.load(
            context,
            Constants.ADMOB_INTERSTITIAL_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isLoadingAd = false
                    Log.d("InterstitialAd", "Ad was loaded.")
                }
                
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    interstitialAd = null
                    isLoadingAd = false
                    Log.d("InterstitialAd", "Ad failed to load: ${loadAdError.message}")
                }
            }
        )
    }
    
    fun showAd(activity: Activity, onAdCompleteListener: () -> Unit = {}) {
        if (interstitialAd != null) {
            interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    onAdCompleteListener()
                    loadAd()
                    Log.d("InterstitialAd", "Ad dismissed fullscreen content.")
                }
                
                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    interstitialAd = null
                    onAdCompleteListener()
                    loadAd()
                    Log.d("InterstitialAd", "Ad failed to show fullscreen content: ${adError.message}")
                }
                
                override fun onAdShowedFullScreenContent() {
                    Log.d("InterstitialAd", "Ad showed fullscreen content.")
                }
            }
            
            interstitialAd?.show(activity)
        } else {
            Log.d("InterstitialAd", "The interstitial ad wasn't ready yet.")
            onAdCompleteListener()
            loadAd()
        }
    }
    
    fun isAdReady(): Boolean {
        return interstitialAd != null
    }
}