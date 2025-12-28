package com.example.carilaundry.ui.feature.customer.detail_pesanan

import com.example.carilaundry.domain.model.Laundry

sealed interface OrderUiState {
    
    // State Loading
    data object Loading : OrderUiState
    
    // State Error
    data class Error(val message: String) : OrderUiState

    // State Success (Menampilkan Data)
    data class Success(
        val laundry: Laundry,
        val weightInput: String = "",
        val estimatedPrice: Double = 0.0,
        val selectedService: String = "Cuci & Lipat"
    ) : OrderUiState
}
