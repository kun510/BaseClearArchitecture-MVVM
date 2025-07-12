package com.example.basecleararchitecture.utils.extensions

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

// Context Extensions
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

// Flow Extensions
fun <T> Flow<T>.onLoading(action: suspend () -> Unit): Flow<T> = onStart { action() }

fun <T> Flow<T>.onError(action: suspend (Throwable) -> Unit): Flow<T> = catch { throwable ->
    action(throwable)
    throw throwable
}

// Compose Extensions
@Composable
fun ShowToast(message: String) {
    val context = LocalContext.current
    LaunchedEffect(message) {
        context.showToast(message)
    }
}