package com.example.carilaundry.ui.feature.customer.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.usecase.GetLaundryListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(
    private val getLaundryListUseCase: GetLaundryListUseCase
) : ViewModel() {

    // Inisialisasi state awal sebagai Loading
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getLaundryList()
    }

    fun getLaundryList() {
        viewModelScope.launch {
            // 1. Set Loading state
            _uiState.value = HomeUiState.Loading

            try {
                // 2. Ambil data
                val list = getLaundryListUseCase()

                // 3. Jika sukses: Emit Success dengan data
                _uiState.value = HomeUiState.Success(laundryList = list)
            } catch (e: IOException) {
                // 4. Jika error: Emit Error dengan pesan
                _uiState.value = HomeUiState.Error(message = "Gagal memuat data: ${e.message}")
            }
        }
    }
}
