package com.example.basecleararchitecture.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.basecleararchitecture.domain.model.User
import com.example.basecleararchitecture.presentation.ui.components.BannerAdView
import com.example.basecleararchitecture.presentation.ui.components.UserCard
import com.example.basecleararchitecture.presentation.viewmodel.UserViewModel
import com.example.basecleararchitecture.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: UserViewModel,
    onShowInterstitialAd: () -> Unit
) {
    val usersState by viewModel.users.collectAsStateWithLifecycle()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Clean Architecture Demo") }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Banner Ad
        BannerAdView(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Refresh Button
        Button(
            onClick = { viewModel.refreshUsers() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Refresh Users")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Show Interstitial Ad Button
        Button(
            onClick = onShowInterstitialAd,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show Interstitial Ad")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Users List
        when (usersState) {
            is Resource.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is Resource.Success -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(usersState.data ?: emptyList()) { user ->
                        UserCard(user = user)
                    }
                }
            }
            is Resource.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = usersState.message ?: "Unknown error",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}