package com.target.targetcasestudy.data

import com.target.targetcasestudy.api.Deal
import com.target.targetcasestudy.api.DealApi
import com.target.targetcasestudy.api.DealResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DealRepository @Inject constructor(
    private val dealApi: DealApi
) {
    
    suspend fun getDeals(): Result<DealResponse> {
        return try {
            val response = dealApi.retrieveDeals()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getDeal(dealId: Int): Result<Deal> {
        return try {
            val response = dealApi.retrieveDeal(dealId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 