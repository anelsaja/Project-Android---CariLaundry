package com.example.carilaundry.ui.feature.customer.detail_pesanan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.R
import com.example.carilaundry.domain.model.Laundry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Ambil ID Laundry dari navigasi
    private val laundryId: String? = savedStateHandle["laundryId"]

    private val _uiState = MutableStateFlow<OrderUiState>(OrderUiState.Loading)
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Harga per kg
    private val pricePerKg = 6000.0

    init {
        if (laundryId != null) {
            fetchLaundryData(laundryId)
        } else {
            _uiState.value = OrderUiState.Error("ID Laundry tidak ditemukan")
        }
    }

    private fun fetchLaundryData(id: String) {
        viewModelScope.launch {
            _uiState.value = OrderUiState.Loading

            // Ambil data dari collection 'owners'
            firestore.collection("owners").document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        try {
                            val name = document.getString("businessName")
                                ?: document.getString("name") ?: "Tanpa Nama"
                            val address = document.getString("address") ?: "-"

                            val laundry = Laundry(
                                id = document.id,
                                name = name,
                                address = address,
                                distance = "-",
                                imageRes = R.drawable.icon
                            )
                            _uiState.value = OrderUiState.Success(laundry = laundry)
                        } catch (e: Exception) {
                            _uiState.value = OrderUiState.Error("Gagal memproses data")
                        }
                    } else {
                        _uiState.value = OrderUiState.Error("Data laundry tidak ditemukan")
                    }
                }
                .addOnFailureListener {
                    _uiState.value = OrderUiState.Error(it.message ?: "Terjadi kesalahan")
                }
        }
    }

    fun onWeightChanged(weight: String) {
        val currentState = _uiState.value
        if (currentState is OrderUiState.Success) {
            val weightValue = weight.toDoubleOrNull() ?: 0.0
            val newPrice = weightValue * pricePerKg

            _uiState.update {
                currentState.copy(
                    weightInput = weight,
                    estimatedPrice = newPrice
                )
            }
        }
    }

    // --- FUNGSI BARU: KIRIM PESANAN KE FIRESTORE ---
    fun submitOrder(
        serviceType: String,
        notes: String,
        pickupDate: String,
        deliveryAddress: String,
        paymentMethod: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val currentState = _uiState.value
        val currentUser = auth.currentUser

        if (currentUser == null) {
            onError("Anda harus login terlebih dahulu")
            return
        }

        if (currentState is OrderUiState.Success && laundryId != null) {
            // Data yang akan dikirim ke database
            val orderData = hashMapOf(
                "laundryId" to laundryId, // Agar masuk ke Owner yang benar
                "customerId" to currentUser.uid,
                "customerName" to (currentUser.displayName ?: "Pelanggan"),
                "status" to "Menunggu Konfirmasi", // Status Awal
                "serviceType" to serviceType,
                "weight" to (currentState.weightInput.toDoubleOrNull() ?: 0.0),
                "totalPrice" to currentState.estimatedPrice,
                "notes" to notes,
                "pickupDate" to pickupDate,
                "address" to deliveryAddress,
                "paymentMethod" to paymentMethod,
                "timestamp" to FieldValue.serverTimestamp() // Waktu pesanan dibuat
            )

            // Simpan ke collection 'orders'
            firestore.collection("orders")
                .add(orderData)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    onError(e.message ?: "Gagal membuat pesanan")
                }
        } else {
            onError("Data belum siap")
        }
    }
}
