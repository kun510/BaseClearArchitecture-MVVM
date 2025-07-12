package com.example.basecleararchitecture.presentation.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basecleararchitecture.base.BaseActivity
import com.example.basecleararchitecture.databinding.ActivityMainBinding
import com.example.basecleararchitecture.presentation.adapter.UserAdapter
import com.example.basecleararchitecture.presentation.viewmodel.UserViewModel
import com.example.basecleararchitecture.utils.Resource
import com.example.basecleararchitecture.ads.AppOpenAdManager
import com.example.basecleararchitecture.ads.BannerAdManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    
    private val viewModel: UserViewModel by viewModels()
    
    @Inject
    lateinit var userAdapter: UserAdapter
    
    @Inject
    lateinit var bannerAdManager: BannerAdManager
    
    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
    
    override fun setupViews() {
        setSupportActionBar(binding.toolbar)
        
        // Setup RecyclerView
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
        
        // Setup Banner Ad
        bannerAdManager.loadBannerAd(binding.adView)
        
        // Setup click listeners
        binding.btnRefresh.setOnClickListener {
            viewModel.refreshUsers()
        }
        
        binding.btnShowInterstitial.setOnClickListener {
            showInterstitialAd()
        }
    }
    
    override fun setupObservers() {
        lifecycleScope.launch {
            viewModel.users.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                        binding.tvError.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.tvError.visibility = View.GONE
                        
                        resource.data?.let { users ->
                            userAdapter.updateItems(users)
                        }
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = resource.message ?: "Unknown error"
                    }
                }
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Show app open ad
        showAppOpenAd(object : AppOpenAdManager.OnShowAdCompleteListener {
            override fun onShowAdComplete() {
                // Continue with app flow
            }
        })
    }
}