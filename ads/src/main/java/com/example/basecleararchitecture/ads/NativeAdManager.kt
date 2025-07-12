package com.example.basecleararchitecture.ads

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NativeAdManager @Inject constructor(
    private val context: Context,
    private val adConstants: AdConstants
) {
    
    private var nativeAd: NativeAd? = null
    private var isLoadingAd = false
    
    fun loadNativeAd(onAdLoadedListener: (NativeAd) -> Unit, onAdFailedListener: (String) -> Unit) {
        if (isLoadingAd) {
            return
        }
        
        isLoadingAd = true
        
        val adLoader = AdLoader.Builder(context, adConstants.nativeAdId)
            .forNativeAd { ad ->
                nativeAd = ad
                isLoadingAd = false
                onAdLoadedListener(ad)
                Log.d("NativeAd", "Native ad loaded successfully")
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    isLoadingAd = false
                    onAdFailedListener(adError.message)
                    Log.d("NativeAd", "Native ad failed to load: ${adError.message}")
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .setReturnUrlsForImageAssets(false)
                    .build()
            )
            .build()
        
        adLoader.loadAd(AdRequest.Builder().build())
    }
    
    fun destroyAd() {
        nativeAd?.destroy()
        nativeAd = null
    }
    
    fun getCurrentAd(): NativeAd? = nativeAd
}