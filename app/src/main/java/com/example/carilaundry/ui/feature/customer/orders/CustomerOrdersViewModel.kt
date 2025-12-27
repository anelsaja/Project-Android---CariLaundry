package com.example.carilaundry.ui.feature.customer.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.model.CustomerOrder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CustomerOrdersViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OrdersUiState())
    val uiState: StateFlow<OrdersUiState> = _uiState.asStateFlow()

    init {
        loadOrders()
    }

    private fun loadOrders() {
        viewModelScope.launch {
            // 1. Set Loading
            _uiState.update { it.copy(isLoading = true) }

            // Simulasi network delay
            delay(1000)

            // 2. Data Dummy (Pindahkan dari Screen ke sini)
            val dummyOrders = listOf(
                CustomerOrder("1", "Cuci & Lipat", "5 kg", "Rp 40.000", "Proses Pengerjaan", "Besok, 14:00"),
                CustomerOrder("2", "Cuci Kering", "2 pcs", "Rp 75.000", "Menunggu Kurir", "Hari ini, 17:00"),
                CustomerOrder("3", "Setrika Saja", "3 kg", "Rp 15.000", "Selesai", "Kemarin")
            )

            // 3. Update State
            _uiState.update {
                it.copy(
                    isLoading = false,
                    orders = dummyOrders
                )
            }
        }
    }
}