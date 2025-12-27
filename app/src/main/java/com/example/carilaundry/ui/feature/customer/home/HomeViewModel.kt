package com.example.carilaundry.ui.feature.customer.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.usecase.GetLaundryListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel(
    private val getLaundryListUseCase: GetLaundryListUseCase
) : ViewModel() {

    // Inisialisasi state awal (Loading false, List kosong, Error null)
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getLaundryList()
    }

    fun getLaundryList() {
        viewModelScope.launch {
            // 1. Set Loading = true, hapus error lama
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                // 2. Ambil data
                val list = getLaundryListUseCase()

                // 3. Jika sukses: Matikan loading, masukkan list
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        laundryList = list,
                        errorMessage = null
                    )
                }
            } catch (e: IOException) {
                // 4. Jika error: Matikan loading, simpan pesan error
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        errorMessage = "Gagal memuat data: ${e.message}"
                    )
                }
            }
        }
    }
}