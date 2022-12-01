package dev.jpvillegas.bbcnewsrss.presentation.web_view_screen

import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewScreen(context: Context, url: String) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp),
        )

        AndroidView(factory = {
            WebView(context).apply {
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        })
    }
}