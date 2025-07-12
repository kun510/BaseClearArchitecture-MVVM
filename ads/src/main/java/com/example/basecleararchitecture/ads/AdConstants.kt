package com.example.basecleararchitecture.ads

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdConstants @Inject constructor() {
    
    // AdMob Test IDs - Replace with your actual Ad Unit IDs
    val appId = "ca-app-pub-3940256099942544~3347511713"
    val bannerAdId = "ca-app-pub-3940256099942544/6300978111"
    val interstitialAdId = "ca-app-pub-3940256099942544/1033173712"
    val nativeAdId = "ca-app-pub-3940256099942544/2247696110"
    val appOpenAdId = "ca-app-pub-3940256099942544/3419835294"
}