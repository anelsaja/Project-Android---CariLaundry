package com.example.carilaundry.ui.feature.customer.profil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.model.UserProfile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            delay(500) // Simulasi loading

            // Data Dummy
            val dummyUser = UserProfile(
                name = "Anel Saja",
                email = "anel.rifki@email.com",
                phone = "+62 812-3456-7890",
                address = "Jl. Sudirman No. 123, Jakarta",
                initials = "AS"
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    userProfile = dummyUser
                )
            }
        }
    }

    fun logout() {
        // Di sini nanti bisa tambahkan logika hapus session/token
    }
}