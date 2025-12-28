package com.example.carilaundry.ui.feature.owner.login

data class OwnerLoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

sealed interface OwnerLoginEvent {
    data class EmailChanged(val email: String) : OwnerLoginEvent
    data class PasswordChanged(val password: String) : OwnerLoginEvent
    object LoginClicked : OwnerLoginEvent
    object ErrorDismissed : OwnerLoginEvent
}
