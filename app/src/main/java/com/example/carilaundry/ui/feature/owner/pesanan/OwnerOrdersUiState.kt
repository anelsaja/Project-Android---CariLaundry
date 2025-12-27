package com.example.carilaundry.ui.feature.owner.pesanan

import com.example.carilaundry.domain.model.OwnerOrder

data class OwnerOrdersUiState(
    val isLoading: Boolean = false,
    val orders: List<OwnerOrder> = emptyList(),
    val errorMessage: String? = null
)