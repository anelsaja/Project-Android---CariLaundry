package com.example.carilaundry.ui.feature.customer.detail_pesanan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.model.Laundry
import com.example.carilaundry.domain.repository.LaundryRepository
import kotlinx.coroutines.launch

// Status UI untuk halaman Order
sealed interface OrderUiState {
    object Loading : OrderUiState
    object Error : OrderUiState
    data class Success(
        val laundry: Laundry,
        val weightInput: String = "",
        val estimatedPrice: Double = 0.0
    ) : OrderUiState
}

class OrderViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: LaundryRepository
) : ViewModel() {

    private val laundryId: String = checkNotNull(savedStateHandle["id"]) // Sesuai nama argumen di NavGraph

    var uiState: OrderUiState by mutableStateOf(OrderUiState.Loading)
        private set

    init {
        loadLaundryData()
    }

    private fun loadLaundryData() {
        viewModelScope.launch {
            val laundry = repository.getLaundryById(laundryId)
            if (laundry != null) {
                uiState = OrderUiState.Success(laundry = laundry)
            } else {
                uiState = OrderUiState.Error
            }
        }
    }

    // Fungsi untuk update berat cucian dan hitung harga
    fun onWeightChanged(newWeight: String) {
        val currentState = uiState
        if (currentState is OrderUiState.Success) {
            val weight = newWeight.toDoubleOrNull() ?: 0.0
            // Asumsi harga per kg ada di dummy/model, atau kita set default 5000 dulu
            val pricePerKg = 5000.0
            val total = weight * pricePerKg

            uiState = currentState.copy(
                weightInput = newWeight,
                estimatedPrice = total
            )
        }
    }
}