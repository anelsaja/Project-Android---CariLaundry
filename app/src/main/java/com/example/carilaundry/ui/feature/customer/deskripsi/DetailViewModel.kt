package com.example.carilaundry.ui.feature.customer.deskripsi

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.repository.LaundryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: LaundryRepository
) : ViewModel() {

    // Mengambil ID dari argumen navigasi (pastikan di AppNavigation namanya "laundryId")
    private val laundryId: String = checkNotNull(savedStateHandle["laundryId"])

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        getLaundryDetail()
    }

    private fun getLaundryDetail() {
        viewModelScope.launch {
            // 1. Set Loading
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // 2. Ambil data dari Repository
            val data = repository.getLaundryById(laundryId)

            // 3. Update UI
            if (data != null) {
                _uiState.update {
                    it.copy(isLoading = false, laundry = data)
                }
            } else {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Data laundry tidak ditemukan")
                }
            }
        }
    }
}