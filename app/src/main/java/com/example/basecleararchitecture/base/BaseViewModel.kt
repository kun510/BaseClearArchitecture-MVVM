package com.example.basecleararchitecture.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basecleararchitecture.ads.NativeAdManager
import com.google.android.gms.ads.nativead.NativeAd
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    
    @Inject
    lateinit var nativeAdManager: NativeAdManager
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    private val _nativeAd = MutableStateFlow<NativeAd?>(null)
    val nativeAd: StateFlow<NativeAd?> = _nativeAd
    
    protected fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
    
    protected fun setError(error: String?) {
        _error.value = error
    }
    
    protected fun clearError() {
        _error.value = null
    }
    
    protected fun loadNativeAd() {
        // Check if nativeAdManager is initialized
        if (::nativeAdManager.isInitialized) {
            viewModelScope.launch {
                nativeAdManager.loadNativeAd(
                    onAdLoadedListener = { ad ->
                        _nativeAd.value = ad
                    },
                    onAdFailedListener = { errorMessage ->
                        setError("Failed to load native ad: $errorMessage")
                    }
                )
            }
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        if (::nativeAdManager.isInitialized) {
            nativeAdManager.destroyAd()
        }
    }
}