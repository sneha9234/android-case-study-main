package com.target.targetcasestudy.api

import retrofit2.http.GET
import retrofit2.http.Path

internal const val BASE_URL = "https://api.target.com/mobile_case_study_deals/v1/"

interface DealApi {

  @GET("deals")
  suspend fun retrieveDeals(): DealResponse

  @GET("deals/{dealId}")
  suspend fun retrieveDeal(@Path("dealId") id: String): Deal
} 