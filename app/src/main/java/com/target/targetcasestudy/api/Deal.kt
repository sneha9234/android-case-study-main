package com.target.targetcasestudy.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Deal(
  val id: Int,
  val title: String,
  val aisle: String,
  val description: String,
  @Json(name = "image_url")
  val imageUrl: String,
  @Json(name = "regular_price")
  val regularPrice: Price,
  @Json(name = "sale_price")
  val salePrice: Price? = null,
  val fulfillment: String,
  val availability: String
)
