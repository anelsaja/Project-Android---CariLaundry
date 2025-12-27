package com.example.carilaundry.ui.feature.customer.home

import com.example.carilaundry.domain.model.Laundry

data class HomeUiState(
    val isLoading: Boolean = false,
    val laundryList: List<Laundry> = emptyList(),
    val errorMessage: String? = null
)