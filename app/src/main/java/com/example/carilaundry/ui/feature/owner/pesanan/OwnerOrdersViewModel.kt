package com.example.carilaundry.ui.feature.owner.pesanan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.model.OwnerOrder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OwnerOrdersViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OwnerOrdersUiState())
    val uiState: StateFlow<OwnerOrdersUiState> = _uiState.asStateFlow()

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    init {
        loadIncomingOrders()
    }

    private fun loadIncomingOrders() {
        // 1. Ambil ID Owner yang sedang login
        val ownerId = auth.currentUser?.uid

        if (ownerId == null) {
            _uiState.update { it.copy(isLoading = false, errorMessage = "User tidak terlogin") }
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        // 2. Query ke Collection 'orders'
        // Mencari pesanan yang ditujukan untuk Owner ini (field 'laundryId')
        firestore.collection("orders")
            .whereEqualTo("laundryId", ownerId)
            // Urutkan dari pesanan terbaru (Pastikan timestamp ada)
            // .orderBy("timestamp", Query.Direction.DESCENDING) // Aktifkan jika sudah ada index di Firestore
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    Log.e("OwnerOrders", "Error listening updates: ${error.message}")
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = "Gagal memuat data: ${error.message}")
                    }
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    // 3. Konversi Dokumen Firestore ke Data Model Aplikasi
                    val ordersList = snapshots.documents.mapNotNull { doc ->
                        try {
                            OwnerOrder(
                                id = doc.id,
                                customerName = doc.getString("customerName") ?: "Tanpa Nama",
                                address = doc.getString("address") ?: "Alamat tidak tersedia",
                                service = doc.getString("serviceType") ?: "Reguler",
                                weightEstimate = "${doc.getDouble("weight") ?: 0.0} kg",
                                priceText = "Rp ${doc.getLong("totalPrice") ?: 0}",
                                // Pastikan di model OwnerOrder Anda sudah menambahkan field 'status'
                                status = doc.getString("status") ?: "Menunggu Konfirmasi"
                            )
                        } catch (e: Exception) {
                            Log.e("OwnerOrders", "Error parsing doc ${doc.id}: ${e.message}")
                            null
                        }
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            orders = ordersList,
                            errorMessage = if (ordersList.isEmpty()) "Belum ada pesanan masuk" else null
                        )
                    }
                }
            }
    }

    // Fungsi untuk mengubah status pesanan (Dinamis)
    fun updateOrderStatus(orderId: String, newStatus: String) {
        viewModelScope.launch {
            firestore.collection("orders").document(orderId)
                .update("status", newStatus)
                .addOnSuccessListener {
                    Log.d("OwnerOrders", "Status berhasil diubah ke: $newStatus")
                    // Tidak perlu update UI manual, listener di atas akan otomatis mendeteksi perubahan
                }
                .addOnFailureListener { e ->
                    Log.e("OwnerOrders", "Gagal update status: ${e.message}")
                    _uiState.update {
                        it.copy(errorMessage = "Gagal mengubah status: ${e.message}")
                    }
                }
        }
    }
}
