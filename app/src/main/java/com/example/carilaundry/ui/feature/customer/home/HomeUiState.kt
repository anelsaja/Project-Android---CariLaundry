package com.example.carilaundry.ui.feature.customer.home

import com.example.carilaundry.domain.model.Laundry

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val laundryList: List<Laundry>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
