package com.example.carilaundry.ui.feature.customer.register

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false, // Penanda jika registrasi berhasil
    val errorMessage: String? = null
)