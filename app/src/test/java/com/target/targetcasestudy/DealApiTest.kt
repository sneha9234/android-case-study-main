package com.target.targetcasestudy

import com.target.targetcasestudy.api.Deal
import com.target.targetcasestudy.api.DealResponse
import com.target.targetcasestudy.api.Price
import org.junit.Test
import org.junit.Assert.*

class DealApiTest {

    @Test
    fun testDealDataClass() {
        val price = Price(
            amountInCents = 1999,
            currencySymbol = "$",
            displayString = "$19.99"
        )
        
        val deal = Deal(
            id = 1,
            title = "Test Product",
            aisle = "A1",
            description = "Test description",
            imageUrl = "https://example.com/image.jpg",
            regularPrice = price,
            salePrice = null,
            fulfillment = "Online",
            availability = "In stock"
        )
        
        assertEquals(1, deal.id)
        assertEquals("Test Product", deal.title)
        assertEquals("A1", deal.aisle)
        assertEquals(1999, deal.regularPrice.amountInCents)
        assertNull(deal.salePrice)
    }
    
    @Test
    fun testDealResponse() {
        val deal = Deal(
            id = 1,
            title = "Test Product",
            aisle = "A1", 
            description = "Test description",
            imageUrl = "https://example.com/image.jpg",
            regularPrice = Price(1999, "$", "$19.99"),
            salePrice = null,
            fulfillment = "Online",
            availability = "In stock"
        )
        
        val response = DealResponse(deals = listOf(deal))
        
        assertEquals(1, response.deals.size)
        assertEquals("Test Product", response.deals[0].title)
    }
} 