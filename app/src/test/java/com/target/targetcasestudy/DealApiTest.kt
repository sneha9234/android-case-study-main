package com.target.targetcasestudy

import com.target.targetcasestudy.api.DealApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class DealApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var dealApi: DealApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        dealApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DealApi::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testRetrieveDeals() = runTest {
        val jsonResponse = """
            {
                "deals": [
                    {
                        "id": 1,
                        "title": "Test Deal",
                        "description": "Description",
                        "aisle": "A1",
                        "image_url": "",
                        "regular_price": {
                            "amount_in_cents": 100,
                            "currency_symbol": "$",
                            "display_string": "$1.00"
                        },
                        "sale_price": null,
                        "fulfillment": "",
                        "availability": ""
                    }
                ]
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(jsonResponse))

        val response = dealApi.retrieveDeals()
        val request = mockWebServer.takeRequest()

        assertEquals("/deals", request.path)
        assertEquals(1, response.deals.size)
        assertEquals("Test Deal", response.deals[0].title)
    }

    @Test
    fun testRetrieveDeal() = runTest {
        val jsonResponse = """
            {
                "id": 123,
                "title": "Test Deal",
                "description": "This is a test deal description.",
                "regular_price": {
                    "amount_in_cents": 1000,
                    "currency_symbol": "$",
                    "display_string": "$10.00"
                },
                "sale_price": {
                    "amount_in_cents": 500,
                    "currency_symbol": "$", 
                    "display_string": "$5.00"
                },
                "image_url": "http://example.com/image.png",
                "fulfillment": "Online",
                "availability": "In stock",
                "aisle": "A1"
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(jsonResponse))

        val response = dealApi.retrieveDeal("123")
        val request = mockWebServer.takeRequest()

        assertEquals("/deals/123", request.path)
        assertEquals(123, response.id)
        assertEquals("Test Deal", response.title)
    }

    @Test
    fun testApiError() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR))

        try {
            dealApi.retrieveDeal("123")
            fail("Expected an HttpException")
        } catch (e: HttpException) {
            assertEquals(HttpURLConnection.HTTP_INTERNAL_ERROR, e.code())
        }
    }
} 