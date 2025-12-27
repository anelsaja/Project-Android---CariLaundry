package com.example.carilaundry.ui.feature.customer.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CustomerLoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Fungsi untuk update input email saat user mengetik
    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail, errorMessage = null) }
    }

    // Fungsi untuk update input password saat user mengetik
    fun onPasswordChange(newPassword: String) {
        _uiState.update { it.copy(password = newPassword, errorMessage = null) }
    }

    // Fungsi Login
    fun login() {
        val currentEmail = _uiState.value.email
        val currentPassword = _uiState.value.password

        // Validasi Sederhana
        if (currentEmail.isEmpty() || currentPassword.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "Email dan Password tidak boleh kosong") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Simulasi Network Call (2 detik)
            delay(2000)

            // Logika Dummy: Email harus mengandung "@" dan pass > 3 karakter
            if (currentEmail.contains("@") && currentPassword.length > 3) {
                _uiState.update { it.copy(isLoading = false, isLoginSuccess = true) }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Email atau Password salah (Coba: email@test.com / 1234)"
                    )
                }
            }
        }
    }

    // Reset state setelah navigasi sukses (agar kalau back, form bersih/tidak error)
    fun resetState() {
        _uiState.update { LoginUiState() }
    }
}