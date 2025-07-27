package com.target.targetcasestudy

import androidx.lifecycle.SavedStateHandle
import com.target.targetcasestudy.api.Deal
import com.target.targetcasestudy.api.Price
import com.target.targetcasestudy.data.DealRepository
import com.target.targetcasestudy.ui.DealDetailViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

@ExperimentalCoroutinesApi
class DealDetailViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var repository: DealRepository

    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: DealDetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `when viewmodel is initialized it should load deal successfully`() = runTest {
        val dealId = "1"
        val mockDeal = Deal(
            id = 1,
            title = "Test Deal",
            aisle = "A1",
            description = "Description",
            imageUrl = "",
            regularPrice = Price(100, "$", "$1.00"),
            salePrice = null,
            fulfillment = "online",
            availability = "in stock"
        )
        savedStateHandle = SavedStateHandle(mapOf("dealId" to dealId))
        coEvery { repository.getDeal(dealId) } returns mockDeal

        viewModel = DealDetailViewModel(repository, savedStateHandle)

        val deal = viewModel.deal.value
        assertEquals(mockDeal, deal)
        assertNull(viewModel.error.value)
        assertEquals(false, viewModel.isLoading.value)
    }

    @Test
    fun `when repository throws an error it should be caught`() = runTest {
        val dealId = "1"
        val errorMessage = "Network Error"
        savedStateHandle = SavedStateHandle(mapOf("dealId" to dealId))
        coEvery { repository.getDeal(dealId) } throws RuntimeException(errorMessage)

        viewModel = DealDetailViewModel(repository, savedStateHandle)

        assertNull(viewModel.deal.value)
        assertEquals(errorMessage, viewModel.error.value)
        assertEquals(false, viewModel.isLoading.value)
    }

    @Test
    fun `clearError should set error to null`() = runTest {
        val dealId = "1"
        val errorMessage = "Network Error"
        savedStateHandle = SavedStateHandle(mapOf("dealId" to dealId))
        coEvery { repository.getDeal(dealId) } throws RuntimeException(errorMessage)
        viewModel = DealDetailViewModel(repository, savedStateHandle)
        assertEquals(errorMessage, viewModel.error.value)

        viewModel.clearError()

        assertNull(viewModel.error.value)
    }
} 