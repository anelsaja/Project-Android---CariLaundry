package com.example.carilaundry.ui.feature.customer.detail_pesanan

import com.example.carilaundry.domain.model.Laundry

data class OrderUiState(
    val isLoading: Boolean = false,
    val laundry: Laundry? = null,
    val errorMessage: String? = null,

    // State Form Order
    val weightInput: String = "",
    val estimatedPrice: Double = 0.0,
    val selectedService: String = "Cuci & Lipat"
)