package com.target.targetcasestudy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.target.targetcasestudy.R
import com.target.targetcasestudy.api.Deal
import com.target.targetcasestudy.api.Price
import com.target.targetcasestudy.ui.theme.GrayDark
import com.target.targetcasestudy.ui.theme.GrayDarkest
import com.target.targetcasestudy.ui.theme.GrayLightest
import com.target.targetcasestudy.ui.theme.RedDark
import com.target.targetcasestudy.ui.theme.RobotoFontFamily
import com.target.targetcasestudy.ui.theme.TargetBlack
import com.target.targetcasestudy.ui.theme.TargetRed
import com.target.targetcasestudy.ui.theme.TargetWhite

/**
 * Figma -> https://www.figma.com/design/hIHMSLgHFhWMyQfVp8fZHc/Android-Technical-Screener?node-id=2-904&t=jGbbVgF6VasbgbDF-4
 */
@Composable
fun DealDetailScreen(
    navController: NavController,
    viewModel: DealDetailViewModel = hiltViewModel()
) {
    val deal by viewModel.deal.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.GrayLightest)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .background(Color.TargetRed)
        )

        Scaffold(
            backgroundColor = Color(0xFFF7F7F7),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Details",
                            color = Color.GrayDarkest,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            fontFamily = RobotoFontFamily
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.left_icon),
                                contentDescription = "Back",
                                tint = Color.TargetRed
                            )
                        }
                    },
                    backgroundColor = Color.TargetWhite,
                    elevation = 4.dp
                )
            },
            bottomBar = {
                if (deal != null) {
                    BottomBar()
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
                                    viewModel.loadDeal()
                                }
                            ) {
                                Text("Retry")
                            }
                        }
                    }
                    deal != null -> {
                        DealDetailContent(deal!!)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth()
            .navigationBarsPadding(),
        color = Color.TargetWhite,
        elevation = 12.dp,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 15.dp)) {
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.TargetRed),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "Add to cart",
                    color = Color.TargetWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = RobotoFontFamily
                )
            }
        }
    }
}

@Composable
fun DealDetailContent(deal: Deal) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.background(Color.TargetWhite)) {
            AsyncImage(
                model = deal.imageUrl,
                contentDescription = deal.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )

            Text(
                text = deal.title,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 9.dp),
                fontSize = 18.sp,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp,
                color = Color.TargetBlack
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = deal.salePrice?.displayString ?: deal.regularPrice.displayString,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = RobotoFontFamily,
                    color = Color.RedDark
                )
                deal.salePrice?.let {
                    Text(
                        text = "reg. ${deal.regularPrice.displayString}",
                        fontSize = 12.sp,
                        fontFamily = RobotoFontFamily,
                        color = Color.GrayDarkest
                    )
                }
            }

            Text(
                text = deal.fulfillment,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                fontSize = 14.sp,
                fontFamily = RobotoFontFamily,
                color = Color.GrayDark
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .background(Color.TargetWhite)
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Product details",
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp,
                fontFamily = RobotoFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.GrayDarkest
            )
            Text(
                text = deal.description,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 16.sp,
                fontFamily = RobotoFontFamily,
                color = Color(0xFF888888),
                lineHeight = 20.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DealDetailScreenPreview() {
    val mockDeal = Deal(
        id = 1,
        title = "Short Sleeve Oversized T-Shirt - Wild Fable™",
        aisle = "W20",
        description = "Adult oversized crewneck tee made from 100% cotton for soft feel and comfy wear. Tailored in an oversized silhouette with a crewneck design with short sleeves and drop shoulders. At-hip length for wearing tucked in or out.\n\nWild Fable™: A look for every story.",
        imageUrl = "https://via.placeholder.com/300",
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.GrayLightest)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .background(Color.TargetRed)
        )

        Scaffold(
            backgroundColor = Color(0xFFF7F7F7),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Details",
                            color = Color.GrayDarkest,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            fontFamily = RobotoFontFamily
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(id = R.drawable.left_icon),
                                contentDescription = "Back",
                                tint = Color.TargetRed
                            )
                        }
                    },
                    backgroundColor = Color.TargetWhite,
                    elevation = 4.dp
                )
            },
            bottomBar = {
                BottomBar()
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                DealDetailContent(deal = mockDeal)
            }
        }
    }
} 