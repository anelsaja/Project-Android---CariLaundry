package com.example.carilaundry.ui.feature.customer.deskripsi

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.R
import com.example.carilaundry.domain.model.Laundry
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    // repository dihapus sementara agar kita bisa tembak langsung ke Firestore 'owners'
) : ViewModel() {

    // Mengambil ID dari argumen navigasi
    // Pastikan di NavGraph route-nya: "customer/deskripsi/{laundryId}"
    private val laundryId: String? = savedStateHandle["laundryId"]

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        if (laundryId != null) {
            getLaundryDetail(laundryId)
        } else {
            _uiState.update { it.copy(isLoading = false, errorMessage = "ID Laundry tidak valid") }
        }
    }

    // Fungsi publik agar bisa dipanggil ulang dari UI jika perlu (LaunchedEffect)
    fun getLaundryDetail(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // AKSES LANGSUNG KE COLLECTION 'owners' (Sesuai perbaikan HomeViewModel)
            FirebaseFirestore.getInstance().collection("owners").document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        try {
                            // Logika prioritas pengambilan nama (Business Name -> name)
                            val name = document.getString("businessName")
                                ?: document.getString("name")
                                ?: document.getString("Name")
                                ?: "Tanpa Nama"

                            val address = document.getString("address")
                                ?: document.getString("Address")
                                ?: "-"

                            // Gunakan icon lokal (R.drawable.icon) agar tidak merubah UI logic gambar
                            val laundry = Laundry(
                                id = document.id,
                                name = name,
                                address = address,
                                distance = "-", // Di detail, jarak tidak wajib dihitung ulang
                                imageRes = R.drawable.icon
                            )

                            _uiState.update { it.copy(isLoading = false, laundry = laundry) }

                        } catch (e: Exception) {
                            Log.e("DetailViewModel", "Error parsing: ${e.message}")
                            _uiState.update { it.copy(isLoading = false, errorMessage = "Gagal memproses data") }
                        }
                    } else {
                        _uiState.update { it.copy(isLoading = false, errorMessage = "Data laundry tidak ditemukan di database") }
                    }
                }
                .addOnFailureListener { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = "Gagal memuat: ${e.message}") }
                }
        }
    }
}
