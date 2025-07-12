package com.example.basecleararchitecture.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.basecleararchitecture.ads.BannerAdManager
import com.example.basecleararchitecture.ads.NativeAdManager
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.nativead.NativeAd
import javax.inject.Inject

abstract class BaseAdapter<T, VB : ViewBinding> : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<VB>>() {
    
    @Inject
    lateinit var bannerAdManager: BannerAdManager
    
    @Inject
    lateinit var nativeAdManager: NativeAdManager
    
    protected val items = mutableListOf<T>()
    
    abstract fun getViewBinding(inflater: LayoutInflater, parent: ViewGroup): VB
    abstract fun bind(binding: VB, item: T, position: Int)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val binding = getViewBinding(LayoutInflater.from(parent.context), parent)
        return BaseViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        val item = items[position]
        bind(holder.binding, item, position)
    }
    
    override fun getItemCount(): Int = items.size
    
    fun updateItems(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
    
    fun addItem(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }
    
    fun removeItem(position: Int) {
        if (position >= 0 && position < items.size) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    
    protected fun loadBannerAd(adView: AdView) {
        if (::bannerAdManager.isInitialized) {
            bannerAdManager.loadBannerAd(adView)
        }
    }
    
    protected fun loadNativeAd(onAdLoadedListener: (NativeAd) -> Unit, onAdFailedListener: (String) -> Unit) {
        if (::nativeAdManager.isInitialized) {
            nativeAdManager.loadNativeAd(onAdLoadedListener, onAdFailedListener)
        }
    }
    
    class BaseViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}