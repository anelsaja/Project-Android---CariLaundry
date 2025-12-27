package com.example.carilaundry.ui.feature.owner.detail_pesanan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.model.OwnerOrderDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailOwnerViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Ambil ID dari URL navigasi
    private val orderId: String = checkNotNull(savedStateHandle["id"])

    private val _uiState = MutableStateFlow(DetailOwnerUiState())
    val uiState: StateFlow<DetailOwnerUiState> = _uiState.asStateFlow()

    init {
        loadOrderDetail()
    }

    private fun loadOrderDetail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000)

            // Data Dummy
            val dummy = OwnerOrderDetail(
                id = orderId,
                customerName = "Mas Anel",
                service = "Cuci dan Lipat",
                category = "Baju dan Celana",
                duration = "3 Hari",
                weightKg = 5,
                notes = "Jangan dicampur warna putih",
                pickupMethod = "Jemput",
                time = "05/11/2025",
                address = "Jl. Soekarno Hatta No. 15, Kampungin",
                paymentMethod = "Bayar Tunai",
                subtotalText = "Rp 40.000",
                deliveryFeeText = "Gratis",
                totalText = "Rp 40.000"
            )

            _uiState.update {
                it.copy(isLoading = false, detail = dummy)
            }
        }
    }
}