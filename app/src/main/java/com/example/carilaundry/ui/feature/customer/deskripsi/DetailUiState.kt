package com.example.carilaundry.ui.feature.customer.deskripsi

import com.example.carilaundry.domain.model.Laundry

data class DetailUiState(
    val isLoading: Boolean = false,
    val laundry: Laundry? = null,
    val errorMessage: String? = null
)