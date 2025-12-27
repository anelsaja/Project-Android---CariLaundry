package com.example.carilaundry.ui.feature.owner.pesanan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.model.OwnerOrder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OwnerOrdersViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OwnerOrdersUiState())
    val uiState: StateFlow<OwnerOrdersUiState> = _uiState.asStateFlow()

    init {
        loadIncomingOrders()
    }

    private fun loadIncomingOrders() {
        viewModelScope.launch {
            // 1. Loading
            _uiState.update { it.copy(isLoading = true) }

            // Simulasi Network
            delay(1000)

            // 2. Data Dummy
            val dummyData = List(5) {
                OwnerOrder(
                    id = it.toString(),
                    customerName = if(it % 2 == 0) "Mas Anel" else "Mbak Siti",
                    address = "Jalan Senopati No. ${it + 1}, Kampungin",
                    service = "Cuci & Lipat",
                    weightEstimate = "${3 + it}kg",
                    priceText = "Rp ${24000 + (it * 2000)}"
                )
            }

            // 3. Success
            _uiState.update {
                it.copy(
                    isLoading = false,
                    orders = dummyData
                )
            }
        }
    }
}