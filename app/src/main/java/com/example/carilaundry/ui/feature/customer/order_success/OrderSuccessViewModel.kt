package com.example.carilaundry.ui.feature.customer.order_success

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OrderSuccessViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(OrderSuccessUiState())
    val uiState: StateFlow<OrderSuccessUiState> = _uiState.asStateFlow()

    init {
        // Ambil data yang dikirim lewat navigasi
        // Pastikan di AppNavigation argumennya bernama "laundryName" dan "laundryAddress"
        val name = savedStateHandle.get<String>("laundryName") ?: "Laundry Pilihan"
        val address = savedStateHandle.get<String>("laundryAddress") ?: "Alamat tidak tersedia"

        _uiState.update {
            it.copy(
                laundryName = name,
                laundryAddress = address
            )
        }
    }
}