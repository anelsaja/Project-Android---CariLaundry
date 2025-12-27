package com.example.carilaundry.ui.feature.customer.register

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

sealed interface RegisterEvent {
    data class NameChanged(val name: String) : RegisterEvent
    data class EmailChanged(val email: String) : RegisterEvent
    data class PhoneChanged(val phone: String) : RegisterEvent
    data class AddressChanged(val address: String) : RegisterEvent
    data class PasswordChanged(val password: String) : RegisterEvent
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterEvent
    object RegisterClicked : RegisterEvent
    object ErrorDismissed : RegisterEvent
}
