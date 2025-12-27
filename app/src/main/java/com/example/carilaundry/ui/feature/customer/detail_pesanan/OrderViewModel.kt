package com.example.carilaundry.ui.feature.customer.detail_pesanan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.repository.LaundryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: LaundryRepository
) : ViewModel() {

    // Mengambil ID dari URL navigasi (navArgument "laundryId")
    private val laundryId: String = checkNotNull(savedStateHandle["laundryId"])

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    init {
        loadLaundryData()
    }

    private fun loadLaundryData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val laundry = repository.getLaundryById(laundryId)

            if (laundry != null) {
                _uiState.update {
                    it.copy(isLoading = false, laundry = laundry)
                }
            } else {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Data laundry tidak ditemukan")
                }
            }
        }
    }

    fun onWeightChanged(newWeight: String) {
        val weight = newWeight.toDoubleOrNull() ?: 0.0
        val pricePerKg = 6000.0 // Harga default, bisa diambil dari model Laundry nanti

        _uiState.update {
            it.copy(
                weightInput = newWeight,
                estimatedPrice = weight * pricePerKg
            )
        }
    }

    fun onServiceChanged(newService: String) {
        _uiState.update { it.copy(selectedService = newService) }
    }
}