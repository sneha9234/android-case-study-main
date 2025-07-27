package com.target.targetcasestudy

import android.graphics.Color
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.target.targetcasestudy.ui.DealListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    setStatusBarAndNavigationBarColor()
    
    setContent {
      DealListScreen()
    }
  }

  private fun setStatusBarAndNavigationBarColor() {
    enableEdgeToEdge()
    WindowCompat.setDecorFitsSystemWindows(window, false)

    window.navigationBarColor = Color.WHITE

    val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
    windowInsetsController.isAppearanceLightStatusBars = false
    windowInsetsController.isAppearanceLightNavigationBars = true
  }
}
