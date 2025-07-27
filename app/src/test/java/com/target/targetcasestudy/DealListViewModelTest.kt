package com.target.targetcasestudy

import com.target.targetcasestudy.api.Deal
import com.target.targetcasestudy.api.DealResponse
import com.target.targetcasestudy.api.Price
import com.target.targetcasestudy.data.DealRepository
import com.target.targetcasestudy.ui.DealListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class DealListViewModelTest {

    @Mock
    private lateinit var mockRepository: DealRepository

    private lateinit var viewModel: DealListViewModel
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private val mockDeal = Deal(
        id = 1,
        title = "Test Product",
        aisle = "A1",
        description = "Test description",
        imageUrl = "https://example.com/image.jpg",
        regularPrice = Price(1999, "$", "$19.99"),
        salePrice = Price(1599, "$", "$15.99"),
        fulfillment = "Online",
        availability = "In stock"
    )

    private val mockDealResponse = DealResponse(deals = listOf(mockDeal))

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load deals successfully`() = runTest {
        // Given
        whenever(mockRepository.getDeals()).thenReturn(Result.success(mockDealResponse))

        // When
        viewModel = DealListViewModel(mockRepository)

        // Then
        assertFalse(viewModel.isLoading.first())
        assertEquals(listOf(mockDeal), viewModel.deals.first())
        assertNull(viewModel.error.first())
    }

    @Test
    fun `init should handle repository failure`() = runTest {
        // Given
        val errorMessage = "Network error"
        whenever(mockRepository.getDeals()).thenReturn(Result.failure(Exception(errorMessage)))

        // When
        viewModel = DealListViewModel(mockRepository)

        // Then
        assertFalse(viewModel.isLoading.first())
        assertTrue(viewModel.deals.first().isEmpty())
        assertEquals(errorMessage, viewModel.error.first())
    }

    @Test
    fun `loadDeals should update loading state correctly`() = runTest {
        // Given
        whenever(mockRepository.getDeals()).thenReturn(Result.success(mockDealResponse))

        // When
        viewModel = DealListViewModel(mockRepository)

        // Then - loading should be false after completion
        assertFalse(viewModel.isLoading.first())
    }

    @Test
    fun `clearError should reset error state`() = runTest {
        // Given
        whenever(mockRepository.getDeals()).thenReturn(Result.failure(Exception("Error")))
        viewModel = DealListViewModel(mockRepository)

        // When
        viewModel.clearError()

        // Then
        assertNull(viewModel.error.first())
    }

    @Test
    fun `retry loadDeals should work after error`() = runTest {
        // Given
        whenever(mockRepository.getDeals())
            .thenReturn(Result.failure(Exception("Network error")))
            .thenReturn(Result.success(mockDealResponse))

        viewModel = DealListViewModel(mockRepository)

        // When
        viewModel.clearError()
        viewModel.loadDeals()

        // Then
        assertEquals(listOf(mockDeal), viewModel.deals.first())
        assertNull(viewModel.error.first())
    }
} 