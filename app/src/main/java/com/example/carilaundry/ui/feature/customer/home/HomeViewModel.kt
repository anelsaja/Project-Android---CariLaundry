package com.example.carilaundry.ui.feature.customer.home

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.R
import com.example.carilaundry.domain.model.Laundry
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val laundryList: List<Laundry>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val firestore = FirebaseFirestore.getInstance()

    // Menyimpan lokasi HP Customer saat ini
    private var currentLocation: Location? = null

    // PENTING: Fungsi ini dipanggil dari UI setelah izin lokasi didapat
    @SuppressLint("MissingPermission")
    fun getUserLocationAndFetchLaundries(context: Context) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        // Coba ambil lokasi terakhir HP
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                currentLocation = location
                Log.d("HomeViewModel", "Lokasi HP Customer: ${location.latitude}, ${location.longitude}")
            } else {
                Log.e("HomeViewModel", "Lokasi HP tidak ditemukan (GPS mati/belum dapat sinyal)")
            }
            // Tetap lanjut ambil data laundry walau lokasi null
            fetchLaundries()
        }.addOnFailureListener {
            Log.e("HomeViewModel", "Gagal akses GPS: ${it.message}")
            fetchLaundries()
        }
    }

    private fun fetchLaundries() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            try {
                // Ambil data owners
                val snapshot = firestore.collection("owners").get().await()

                val laundryList = snapshot.documents.mapNotNull { doc ->
                    try {
                        // Ambil Nama & Alamat
                        val name = doc.getString("businessName")
                            ?: doc.getString("name") ?: "Laundry Tanpa Nama"
                        val address = doc.getString("address") ?: "Alamat Belum Diatur"

                        // --- LOGIKA HITUNG JARAK ---

                        // 1. Ambil koordinat laundry dari Firestore (Default 0.0 jika tidak diisi)
                        val laundryLat = doc.getDouble("latitude") ?: 0.0
                        val laundryLng = doc.getDouble("longitude") ?: 0.0

                        // 2. Hitung jarak jika lokasi HP & lokasi Laundry sama-sama ada
                        var distanceString = "- km"
                        var distanceInMeters = Float.MAX_VALUE // Default jarak jauh banget

                        if (currentLocation != null && laundryLat != 0.0 && laundryLng != 0.0) {
                            val results = FloatArray(1)
                            // Rumus hitung jarak antara 2 titik koordinat
                            Location.distanceBetween(
                                currentLocation!!.latitude, currentLocation!!.longitude,
                                laundryLat, laundryLng,
                                results
                            )
                            distanceInMeters = results[0]

                            // Format: Jika < 1000m pakai "m", jika lebih pakai "km"
                            distanceString = if (distanceInMeters < 1000) {
                                "${distanceInMeters.toInt()} m"
                            } else {
                                String.format("%.1f km", distanceInMeters / 1000)
                            }
                        } else if (laundryLat == 0.0) {
                            distanceString = "? (No Loc)" // Penanda laundry belum punya koordinat
                        }

                        // Buat Objek Laundry (Saya tambahkan properti tempDistance untuk sorting)
                        Laundry(
                            id = doc.id,
                            name = name,
                            address = address,
                            distance = distanceString,
                            imageRes = R.drawable.icon
                        ) to distanceInMeters // Return Pair untuk sorting sementara

                    } catch (e: Exception) {
                        Log.e("HomeViewModel", "Error parsing: ${e.message}")
                        null
                    }
                }

                // Urutkan dari yang jarak meternya paling kecil (Terdekat)
                val sortedList = laundryList.sortedBy { it.second }.map { it.first }

                if (sortedList.isEmpty()) {
                    _uiState.value = HomeUiState.Success(emptyList())
                } else {
                    _uiState.value = HomeUiState.Success(sortedList)
                }

            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error("Gagal: ${e.message}")
            }
        }
    }

    // Init default (tanpa lokasi dulu)
    init {
        fetchLaundries()
    }
}
