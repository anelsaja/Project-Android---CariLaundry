package com.example.carilaundry.ui.feature.owner.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OwnerRegisterViewModel : ViewModel() {

    var registerUiState by mutableStateOf(OwnerRegisterUiState())
        private set

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun onEvent(event: OwnerRegisterEvent) {
        when (event) {
            is OwnerRegisterEvent.BusinessNameChanged -> registerUiState = registerUiState.copy(businessName = event.businessName)
            is OwnerRegisterEvent.OwnerNameChanged -> registerUiState = registerUiState.copy(ownerName = event.ownerName)
            is OwnerRegisterEvent.EmailChanged -> registerUiState = registerUiState.copy(email = event.email)
            is OwnerRegisterEvent.PhoneChanged -> registerUiState = registerUiState.copy(phone = event.phone)
            is OwnerRegisterEvent.AddressChanged -> registerUiState = registerUiState.copy(address = event.address)
            is OwnerRegisterEvent.PasswordChanged -> registerUiState = registerUiState.copy(password = event.password)
            is OwnerRegisterEvent.ConfirmPasswordChanged -> registerUiState = registerUiState.copy(confirmPassword = event.confirmPassword)
            is OwnerRegisterEvent.RegisterClicked -> performRegister()
            is OwnerRegisterEvent.ErrorDismissed -> registerUiState = registerUiState.copy(errorMessage = null)
        }
    }

    private fun performRegister() {
        val s = registerUiState

        // Validasi
        if (s.businessName.isBlank() || s.ownerName.isBlank() || s.email.isBlank() || s.password.isBlank()) {
            registerUiState = registerUiState.copy(errorMessage = "Semua field wajib diisi")
            return
        }
        if (s.password.length < 6) {
            registerUiState = registerUiState.copy(errorMessage = "Password minimal 6 karakter")
            return
        }
        if (s.password != s.confirmPassword) {
            registerUiState = registerUiState.copy(errorMessage = "Konfirmasi password tidak cocok")
            return
        }

        registerUiState = registerUiState.copy(isLoading = true, errorMessage = null)

        auth.createUserWithEmailAndPassword(s.email, s.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        val ownerMap = hashMapOf(
                            "businessName" to s.businessName,
                            "ownerName" to s.ownerName,
                            "email" to s.email,
                            "phone" to s.phone,
                            "address" to s.address,
                            "role" to "owner"
                        )

                        db.collection("owners").document(userId).set(ownerMap)
                            .addOnSuccessListener {
                                registerUiState = registerUiState.copy(isLoading = false, isSuccess = true)
                            }
                            .addOnFailureListener { e ->
                                registerUiState = registerUiState.copy(isLoading = false, errorMessage = "Gagal simpan data: ${e.message}")
                            }
                    } else {
                        registerUiState = registerUiState.copy(isLoading = false, errorMessage = "Gagal mendapatkan User ID")
                    }
                } else {
                    registerUiState = registerUiState.copy(isLoading = false, errorMessage = task.exception?.message ?: "Registrasi Gagal")
                }
            }
    }

    fun resetState() {
        registerUiState = OwnerRegisterUiState()
    }
}

//data class OwnerRegisterUiState(
//    val businessName: String = "",
//    val ownerName: String = "",
//    val email: String = "",
//    val phone: String = "",
//    val address: String = "",
//    val password: String = "",
//    val confirmPassword: String = "",
//    val isLoading: Boolean = false,
//    val isSuccess: Boolean = false,
//    val errorMessage: String? = null
//)

sealed interface OwnerRegisterEvent {
    data class BusinessNameChanged(val businessName: String) : OwnerRegisterEvent
    data class OwnerNameChanged(val ownerName: String) : OwnerRegisterEvent
    data class EmailChanged(val email: String) : OwnerRegisterEvent
    data class PhoneChanged(val phone: String) : OwnerRegisterEvent
    data class AddressChanged(val address: String) : OwnerRegisterEvent
    data class PasswordChanged(val password: String) : OwnerRegisterEvent
    data class ConfirmPasswordChanged(val confirmPassword: String) : OwnerRegisterEvent
    object RegisterClicked : OwnerRegisterEvent
    object ErrorDismissed : OwnerRegisterEvent
}