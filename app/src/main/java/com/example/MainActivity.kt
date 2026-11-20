package com.example

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.viewinterop.AndroidView

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge(
      statusBarStyle = SystemBarStyle.dark(Color.parseColor("#0B0F19")),
      navigationBarStyle = SystemBarStyle.dark(Color.parseColor("#0B0F19"))
    )
    setContent { QuizWebViewScreen() }
  }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun QuizWebViewScreen() {
  var webViewRef by remember { mutableStateOf<WebView?>(null) }

  BackHandler(enabled = webViewRef?.canGoBack() == true) {
    webViewRef?.goBack()
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(ComposeColor(0xFF0B0F19))
      .windowInsetsPadding(WindowInsets.safeDrawing)
  ) {
    AndroidView(
      modifier = Modifier.fillMaxSize(),
      factory = { context ->
        WebView(context).apply {
          layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
          )
          setBackgroundColor(Color.parseColor("#0B0F19"))
          settings.javaScriptEnabled = true
          settings.domStorageEnabled = true
          settings.mediaPlaybackRequiresUserGesture = false
          settings.cacheMode = WebSettings.LOAD_DEFAULT
          settings.useWideViewPort = true
          settings.loadWithOverviewMode = true
          webChromeClient = WebChromeClient()
          webViewClient = WebViewClient()
          loadUrl("file:///android_asset/index.html")
          webViewRef = this
        }
      },
      update = { webView -> webViewRef = webView }
    )
  }
}
