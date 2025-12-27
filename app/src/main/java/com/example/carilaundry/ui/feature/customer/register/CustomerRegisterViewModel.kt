package com.example.carilaundry.ui.feature.customer.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CustomerRegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChange(newValue: String) {
        _uiState.update { it.copy(name = newValue, errorMessage = null) }
    }

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue, errorMessage = null) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue, errorMessage = null) }
    }

    fun register() {
        val currentState = _uiState.value

        // 1. Validasi Sederhana
        if (currentState.name.isEmpty() || currentState.email.isEmpty() || currentState.password.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "Semua kolom harus diisi") }
            return
        }

        // 2. Proses Register
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            // Simulasi Delay Network
            delay(2000)

            // Sukses
            _uiState.update {
                it.copy(isLoading = false, isSuccess = true)
            }
        }
    }

    // Reset form jika user kembali ke halaman ini nanti
    fun resetState() {
        _uiState.update { RegisterUiState() }
    }
}