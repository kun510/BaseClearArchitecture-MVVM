package com.example.basecleararchitecture.ads.di

import android.content.Context
import com.example.basecleararchitecture.ads.AdConstants
import com.example.basecleararchitecture.ads.AppOpenAdManager
import com.example.basecleararchitecture.ads.BannerAdManager
import com.example.basecleararchitecture.ads.InterstitialAdManager
import com.example.basecleararchitecture.ads.NativeAdManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdModule {
    
    @Provides
    @Singleton
    fun provideAdConstants(): AdConstants {
        return AdConstants()
    }
    
    @Provides
    @Singleton
    fun provideInterstitialAdManager(
        @ApplicationContext context: Context,
        adConstants: AdConstants
    ): InterstitialAdManager {
        return InterstitialAdManager(context, adConstants)
    }
    
    @Provides
    @Singleton
    fun provideAppOpenAdManager(
        @ApplicationContext context: Context,
        adConstants: AdConstants
    ): AppOpenAdManager {
        return AppOpenAdManager(context, adConstants)
    }
    
    @Provides
    @Singleton
    fun provideNativeAdManager(
        @ApplicationContext context: Context,
        adConstants: AdConstants
    ): NativeAdManager {
        return NativeAdManager(context, adConstants)
    }
    
    @Provides
    @Singleton
    fun provideBannerAdManager(
        @ApplicationContext context: Context,
        adConstants: AdConstants
    ): BannerAdManager {
        return BannerAdManager(context, adConstants)
    }
}