package com.target.targetcasestudy

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.target.targetcasestudy.data.DealRepository
import com.target.targetcasestudy.ui.DealListScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  @Inject
  lateinit var dealRepository: DealRepository

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      DealListScreen()
    }
    
    // Test API call and log response
    testApiCall()
  }
  
  private fun testApiCall() {
    lifecycleScope.launch {
      Log.d("MainActivity", "Starting API call...")
      
      dealRepository.getDeals()
        .onSuccess { response ->
          Log.d("MainActivity", "API call successful!")
          Log.d("MainActivity", "Number of deals: ${response.deals.size}")
          response.deals.forEachIndexed { index, deal ->
            Log.d("MainActivity", "Deal $index: ${deal.title} - ${deal.regularPrice.displayString}")
            if (deal.salePrice != null) {
              Log.d("MainActivity", "  Sale Price: ${deal.salePrice.displayString}")
            }
            Log.d("MainActivity", "  Aisle: ${deal.aisle}, Availability: ${deal.availability}")
            Log.d("MainActivity", "  Image URL: ${deal.imageUrl}")
          }
        }
        .onFailure { error ->
          Log.e("MainActivity", "API call failed: ${error.message}", error)
        }
    }
  }
}
