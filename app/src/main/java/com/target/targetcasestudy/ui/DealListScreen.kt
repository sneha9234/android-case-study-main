package com.target.targetcasestudy.ui

import android.view.LayoutInflater
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.target.targetcasestudy.R
import com.target.targetcasestudy.data.DealItem
import com.target.targetcasestudy.data.StaticData

@Composable
fun DealListScreen(
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier,
  ) {
    items(
      items = StaticData.deals,
      itemContent = { dealItem ->
        Deal(dealItem)
      }
    )
  }
}

@Composable
fun Deal(
  dealItem: DealItem
){
  Row(
    verticalAlignment = Alignment.CenterVertically
  ){
    Image(
      painter = painterResource(id = R.drawable.ic_launcher_foreground),
      contentDescription = "",
      colorFilter = ColorFilter.tint(Color.Gray),
    )
    Column(
      horizontalAlignment = Alignment.Start,
      verticalArrangement = spacedBy(4.dp)
    )
    {
      Text(
        text = dealItem.title,
        modifier = Modifier.fillMaxWidth(),
        style = typography.h6,
        color = Color.Gray
      )
      Divider()
      Text(
        text = dealItem.price,
        modifier = Modifier.fillMaxWidth(),
        style = typography.subtitle1,
        color = Color.Gray
      )
    }
  }
}

@Preview
@Composable
fun ProductPreview(){
    DealListScreen()
}
