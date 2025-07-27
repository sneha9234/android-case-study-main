package com.target.targetcasestudy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.target.targetcasestudy.api.Deal
import com.target.targetcasestudy.api.Price
import com.target.targetcasestudy.ui.theme.GrayDark
import com.target.targetcasestudy.ui.theme.GrayDarkest
import com.target.targetcasestudy.ui.theme.GreenStock
import com.target.targetcasestudy.ui.theme.RedDark
import com.target.targetcasestudy.ui.theme.RobotoFontFamily
import com.target.targetcasestudy.ui.theme.TargetBlack
import com.target.targetcasestudy.ui.theme.TargetRed
import com.target.targetcasestudy.ui.theme.TargetWhite

/**
 * Figma -> https://www.figma.com/design/hIHMSLgHFhWMyQfVp8fZHc/Android-Technical-Screener?node-id=6-2834&t=jGbbVgF6VasbgbDF-4
 */
@Composable
fun DealListScreen(
    modifier: Modifier = Modifier,
    viewModel: DealListViewModel = hiltViewModel()
) {
    val deals by viewModel.deals.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .background(Color.TargetRed)
        )
        
        Column {
            TopAppBar(
                title = {
                    Text(
                        text = "List",
                        color = Color.GrayDarkest,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = RobotoFontFamily,
                        lineHeight = 24.sp,
                    )
                },
                backgroundColor = Color.TargetWhite
            )

            Divider(
                color = Color.LightGray,
                thickness = 1.dp
            )
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.TargetWhite)
                    .navigationBarsPadding()
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    error != null -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Error: $error",
                                color = Color.RedDark,
                                fontFamily = RobotoFontFamily,
                                modifier = Modifier.padding(16.dp)
                            )
                            Button(
                                onClick = {
                                    viewModel.clearError()
                                    viewModel.loadDeals()
                                }
                            ) {
                                Text("Retry")
                            }
                        }
                    }
                    deals.isEmpty() -> {
                        Text(
                            text = "No deals available",
                            fontFamily = RobotoFontFamily,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            items(
                                items = deals,
                                key = { it.id }
                            ) { deal ->
                                DealCard(deal = deal)
                                Divider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = Color(0xFFD6D6D6),
                                    thickness = 0.5.dp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DealCard(deal: Deal) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.TargetWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = deal.imageUrl,
                contentDescription = deal.title,
                modifier = Modifier
                    .size(140.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                alignment = Alignment.CenterStart
            )
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .width(172.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = deal.salePrice?.displayString ?: deal.regularPrice.displayString,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = RobotoFontFamily,
                        lineHeight = 25.sp,
                        color = Color.RedDark
                    )
                    
                    deal.salePrice?.let {
                        Text(
                            text = "reg. ${deal.regularPrice.displayString}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = RobotoFontFamily,
                            lineHeight = 20.sp,
                            color = Color.GrayDarkest
                        )
                    }
                }
                
                Text(
                    text = deal.fulfillment,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = RobotoFontFamily,
                    lineHeight = 16.sp,
                    color = Color.GrayDark,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Column(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = deal.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = RobotoFontFamily,
                        lineHeight = 20.sp,
                        color = Color.TargetBlack,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row {
                        Text(
                            text = deal.availability,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = RobotoFontFamily,
                            lineHeight = 16.sp,
                            color = Color.GreenStock
                        )
                        Text(
                            text = " in aisle ${deal.aisle}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = RobotoFontFamily,
                            lineHeight = 16.sp,
                            color = Color.GrayDark
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DealCardPreview() {
    val mockDeal = Deal(
        id = 1,
        title = "Women's Long Sleeve Denim Jacket - Universal Thread™",
        aisle = "W20",
        description = "Sample description",
        imageUrl = "https://via.placeholder.com/200",
        regularPrice = Price(
            amountInCents = 3499,
            currencySymbol = "$",
            displayString = "$34.99"
        ),
        salePrice = Price(
            amountInCents = 3499,
            currencySymbol = "$",
            displayString = "$34.99"
        ),
        fulfillment = "Online",
        availability = "In stock"
    )
    
    DealCard(deal = mockDeal)
}
