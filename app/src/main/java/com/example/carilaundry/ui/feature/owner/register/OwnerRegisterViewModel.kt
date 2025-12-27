package com.example.carilaundry.ui.feature.owner.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OwnerRegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OwnerRegisterUiState())
    val uiState: StateFlow<OwnerRegisterUiState> = _uiState.asStateFlow()

    // --- Update Functions ---
    fun onBusinessNameChange(v: String) = _uiState.update { it.copy(businessName = v, errorMessage = null) }
    fun onOwnerNameChange(v: String) = _uiState.update { it.copy(ownerName = v, errorMessage = null) }
    fun onEmailChange(v: String) = _uiState.update { it.copy(email = v, errorMessage = null) }
    fun onPhoneChange(v: String) = _uiState.update { it.copy(phone = v, errorMessage = null) }
    fun onAddressChange(v: String) = _uiState.update { it.copy(address = v, errorMessage = null) }
    fun onPasswordChange(v: String) = _uiState.update { it.copy(password = v, errorMessage = null) }
    fun onConfirmPasswordChange(v: String) = _uiState.update { it.copy(confirmPassword = v, errorMessage = null) }

    fun register() {
        val state = _uiState.value

        // 1. Validasi
        if (state.businessName.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Nama usaha wajib diisi") }
            return
        }
        if (state.ownerName.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Nama pemilik wajib diisi") }
            return
        }
        if (state.email.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Email wajib diisi") }
            return
        }
        if (state.password.length < 6) {
            _uiState.update { it.copy(errorMessage = "Password minimal 6 karakter") }
            return
        }
        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(errorMessage = "Konfirmasi password tidak cocok") }
            return
        }

        // 2. Proses Register
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Simulasi Network
            delay(2000)

            // Sukses
            _uiState.update {
                it.copy(isLoading = false, isSuccess = true)
            }
        }
    }

    fun resetState() {
        _uiState.update { OwnerRegisterUiState() }
    }
}