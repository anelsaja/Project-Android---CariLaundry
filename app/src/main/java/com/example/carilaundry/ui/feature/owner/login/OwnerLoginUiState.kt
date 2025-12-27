package com.example.carilaundry.ui.feature.owner.login

data class OwnerLoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false, // Penanda login berhasil
    val errorMessage: String? = null
)