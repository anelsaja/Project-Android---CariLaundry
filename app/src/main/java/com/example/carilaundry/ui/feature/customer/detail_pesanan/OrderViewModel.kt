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

    // Inisialisasi state awal sebagai Loading
    private val _uiState = MutableStateFlow<OrderUiState>(OrderUiState.Loading)
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    init {
        loadLaundryData()
    }

    private fun loadLaundryData() {
        viewModelScope.launch {
            // Set Loading state (redundant in init but good for reload)
            _uiState.value = OrderUiState.Loading

            val laundry = repository.getLaundryById(laundryId)

            if (laundry != null) {
                // Berhasil memuat data -> Pindah ke state Success
                _uiState.value = OrderUiState.Success(
                    laundry = laundry,
                    weightInput = "",
                    estimatedPrice = 0.0
                )
            } else {
                // Gagal memuat data -> Pindah ke state Error
                _uiState.value = OrderUiState.Error("Data laundry tidak ditemukan")
            }
        }
    }

    fun onWeightChanged(newWeight: String) {
        val currentState = _uiState.value
        if (currentState is OrderUiState.Success) {
            val weight = newWeight.toDoubleOrNull() ?: 0.0
            val pricePerKg = 6000.0 // Harga default, bisa diambil dari model Laundry nanti

            // Update state Success dengan data baru
            _uiState.update {
                currentState.copy(
                    weightInput = newWeight,
                    estimatedPrice = weight * pricePerKg
                )
            }
        }
    }

    fun onServiceChanged(newService: String) {
        val currentState = _uiState.value
        if (currentState is OrderUiState.Success) {
            _uiState.update {
                currentState.copy(selectedService = newService)
            }
        }
    }
}
