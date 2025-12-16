package com.example.carilaundry.ui.feature.customer.deskripsi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.model.Laundry
import com.example.carilaundry.domain.repository.LaundryRepository
import kotlinx.coroutines.launch

sealed interface DetailUiState {
    data class Success(val laundry: Laundry) : DetailUiState
    object Error : DetailUiState
    object Loading : DetailUiState
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle, // Ini untuk menangkap ID dari Navigasi
    private val repository: LaundryRepository
) : ViewModel() {

    // Ambil ID yang dikirim lewat navigasi (key-nya nanti kita namakan "laundryId")
    private val laundryId: String = checkNotNull(savedStateHandle["laundryId"])

    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    init {
        getLaundryDetail()
    }

    private fun getLaundryDetail() {
        viewModelScope.launch {
            // Ambil data dari Repository berdasarkan ID
            val laundry = repository.getLaundryById(laundryId)

            if (laundry != null) {
                detailUiState = DetailUiState.Success(laundry)
            } else {
                detailUiState = DetailUiState.Error
            }
        }
    }
}