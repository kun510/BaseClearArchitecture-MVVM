package com.example.basecleararchitecture.ads

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BannerAdManager @Inject constructor(
    private val context: Context,
    private val adConstants: AdConstants
) {
    
    fun createBannerAdView(): AdView {
        val adView = AdView(context)
        adView.adUnitId = adConstants.bannerAdId
        adView.setAdSize(AdSize.BANNER)
        return adView
    }
    
    fun loadBannerAd(adView: AdView, onAdLoadedListener: (() -> Unit)? = null, onAdFailedListener: ((String) -> Unit)? = null) {
        val adRequest = AdRequest.Builder().build()
        
        adView.adListener = object : com.google.android.gms.ads.AdListener() {
            override fun onAdLoaded() {
                Log.d("BannerAd", "Banner ad loaded successfully")
                onAdLoadedListener?.invoke()
            }
            
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("BannerAd", "Banner ad failed to load: ${adError.message}")
                onAdFailedListener?.invoke(adError.message)
            }
        }
        
        adView.loadAd(adRequest)
    }
}