package com.target.targetcasestudy.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.api.Deal
import com.target.targetcasestudy.data.DealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DealDetailViewModel @Inject constructor(
    private val repository: DealRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val dealId: String = savedStateHandle.get<String>("dealId")!!

    private val _deal = MutableStateFlow<Deal?>(null)
    val deal: StateFlow<Deal?> = _deal.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadDeal()
    }

    fun loadDeal() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _deal.value = repository.getDeal(dealId)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
} 