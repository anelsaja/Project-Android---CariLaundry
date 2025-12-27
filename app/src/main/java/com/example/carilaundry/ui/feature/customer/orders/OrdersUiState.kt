package com.example.carilaundry.ui.feature.customer.orders

import com.example.carilaundry.domain.model.CustomerOrder

data class OrdersUiState(
    val isLoading: Boolean = false,
    val orders: List<CustomerOrder> = emptyList(),
    val errorMessage: String? = null
)