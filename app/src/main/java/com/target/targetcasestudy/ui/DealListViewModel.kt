package com.target.targetcasestudy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.api.Deal
import com.target.targetcasestudy.data.DealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DealListViewModel @Inject constructor(
    private val dealRepository: DealRepository
) : ViewModel() {

    private val _deals = MutableStateFlow<List<Deal>>(emptyList())
    val deals: StateFlow<List<Deal>> = _deals.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadDeals()
    }

    fun loadDeals() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            dealRepository.getDeals()
                .onSuccess { response ->
                    _deals.value = response.deals
                }
                .onFailure { throwable ->
                    _error.value = throwable.message ?: "Unknown error occurred"
                }

            _isLoading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }
} 