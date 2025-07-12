package com.example.basecleararchitecture.utils.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import com.example.basecleararchitecture.utils.Constants
import java.util.Date

class AppOpenAdManager(private val context: Context) {
    
    private var appOpenAd: AppOpenAd? = null
    private var isLoadingAd = false
    var isShowingAd = false
    
    private var loadTime: Long = 0
    
    fun loadAd(context: Context) {
        if (isLoadingAd || isAdAvailable()) {
            return
        }
        
        isLoadingAd = true
        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            context,
            Constants.ADMOB_OPEN_APP_ID,
            request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    isLoadingAd = false
                    loadTime = Date().time
                    Log.d("AppOpenAd", "Ad was loaded.")
                }
                
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    isLoadingAd = false
                    Log.d("AppOpenAd", "Ad failed to load: ${loadAdError.message}")
                }
            }
        )
    }
    
    fun showAdIfAvailable(activity: Activity, onShowAdCompleteListener: OnShowAdCompleteListener) {
        if (isShowingAd) {
            Log.d("AppOpenAd", "The app open ad is already showing.")
            return
        }
        
        if (!isAdAvailable()) {
            Log.d("AppOpenAd", "The app open ad is not ready yet.")
            onShowAdCompleteListener.onShowAdComplete()
            loadAd(activity)
            return
        }
        
        Log.d("AppOpenAd", "Will show ad.")
        appOpenAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                appOpenAd = null
                isShowingAd = false
                Log.d("AppOpenAd", "Ad dismissed fullscreen content.")
                onShowAdCompleteListener.onShowAdComplete()
                loadAd(activity)
            }
            
            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                appOpenAd = null
                isShowingAd = false
                Log.d("AppOpenAd", "Ad failed to show fullscreen content.")
                onShowAdCompleteListener.onShowAdComplete()
                loadAd(activity)
            }
            
            override fun onAdShowedFullScreenContent() {
                Log.d("AppOpenAd", "Ad showed fullscreen content.")
            }
        }
        
        isShowingAd = true
        appOpenAd?.show(activity)
    }
    
    private fun isAdAvailable(): Boolean {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)
    }
    
    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference = Date().time - loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }
    
    interface OnShowAdCompleteListener {
        fun onShowAdComplete()
    }
}