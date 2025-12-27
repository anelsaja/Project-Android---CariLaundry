package com.example.carilaundry.ui.feature.owner.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OwnerLoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OwnerLoginUiState())
    val uiState: StateFlow<OwnerLoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue, errorMessage = null) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue, errorMessage = null) }
    }

    fun login() {
        val currentState = _uiState.value

        // 1. Validasi Input
        if (currentState.email.isEmpty() || currentState.password.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "Email dan Password harus diisi") }
            return
        }

        // 2. Proses Login
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Simulasi Network Call
            delay(2000)

            // Logika Dummy (Contoh: email harus mengandung "owner")
            if (currentState.email.contains("owner") || currentState.email == "admin") {
                _uiState.update {
                    it.copy(isLoading = false, isSuccess = true)
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Akun tidak ditemukan. Coba gunakan email 'owner'"
                    )
                }
            }
        }
    }

    // Reset state setelah navigasi berhasil
    fun resetState() {
        _uiState.update { OwnerLoginUiState() }
    }
}