package com.example.carilaundry.ui.feature.owner.profil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.model.OwnerProfile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OwnerProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OwnerProfileUiState())
    val uiState: StateFlow<OwnerProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            // 1. Loading
            _uiState.update { it.copy(isLoading = true) }

            // Simulasi Network
            delay(500)

            // 2. Data Dummy
            val dummyProfile = OwnerProfile(
                name = "Pak Budi",
                role = "Pemilik Laundry",
                email = "budi.laundry@email.com",
                phone = "+62 812-9876-5432",
                address = "Jalan Senopati No. 3, Kampungin",
                initials = "PB"
            )

            // 3. Success
            _uiState.update {
                it.copy(
                    isLoading = false,
                    ownerProfile = dummyProfile
                )
            }
        }
    }

    fun logout() {
        // Logika logout (hapus session, token, dll)
    }
}