package com.target.targetcasestudy

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.target.targetcasestudy.ui.DealDetailScreen
import com.target.targetcasestudy.ui.DealListScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    setStatusBarAndNavigationBarColor()
    
    setContent {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "deal_list") {
          composable("deal_list") {
            DealListScreen(onDealClick = { dealId ->
              navController.navigate("deal_detail/$dealId")
            })
          }
          composable("deal_detail/{dealId}") {
            DealDetailScreen(navController = navController)
          }
      }
    }
  }

  private fun setStatusBarAndNavigationBarColor() {
    enableEdgeToEdge()
    WindowCompat.setDecorFitsSystemWindows(window, false)

    val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
    windowInsetsController.isAppearanceLightStatusBars = false
    windowInsetsController.isAppearanceLightNavigationBars = true
  }
}
