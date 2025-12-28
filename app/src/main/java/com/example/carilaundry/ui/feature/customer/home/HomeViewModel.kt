package com.example.carilaundry.ui.feature.customer.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.R
import com.example.carilaundry.domain.model.Laundry
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// State UI
sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val laundryList: List<Laundry>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val firestore = FirebaseFirestore.getInstance()

    init {
        fetchLaundries()
    }

    private fun fetchLaundries() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            // --- PERUBAHAN PENTING DI SINI ---
            // Kita mengambil data langsung dari collection "owner"
            // Bukan dari "users" lagi
            firestore.collection("owner")
                .get()
                .addOnSuccessListener { documents ->
                    val laundryList = documents.mapNotNull { doc ->
                        try {
                            // Ambil data nama dan alamat dari dokumen Owner
                            val name = doc.getString("name") ?: "Laundry Tanpa Nama"
                            val address = doc.getString("address") ?: "Alamat belum diatur"

                            val distance = "0.5 km"

                            Laundry(
                                id = doc.id,
                                name = name,
                                address = address,
                                distance = distance,
                                imageRes = R.drawable.icon // Pastikan icon ini ada di drawable
                            )
                        } catch (e: Exception) {
                            Log.e("HomeViewModel", "Error parsing laundry: ${e.message}")
                            null
                        }
                    }

                    if (laundryList.isEmpty()) {
                        _uiState.value = HomeUiState.Success(emptyList())
                    } else {
                        _uiState.value = HomeUiState.Success(laundryList)
                    }
                }
                .addOnFailureListener { exception ->
                    _uiState.value = HomeUiState.Error("Gagal memuat data: ${exception.message}")
                }
        }
    }

    fun refreshData() {
        fetchLaundries()
    }
}
