package com.example.carilaundry.ui.feature.customer.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CustomerRegisterViewModel : ViewModel() {

    var registerUiState by mutableStateOf(RegisterUiState())
        private set

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.NameChanged -> registerUiState = registerUiState.copy(name = event.name)
            is RegisterEvent.EmailChanged -> registerUiState = registerUiState.copy(email = event.email)
            is RegisterEvent.PhoneChanged -> registerUiState = registerUiState.copy(phone = event.phone)
            is RegisterEvent.AddressChanged -> registerUiState = registerUiState.copy(address = event.address)
            is RegisterEvent.PasswordChanged -> registerUiState = registerUiState.copy(password = event.password)
            is RegisterEvent.ConfirmPasswordChanged -> registerUiState = registerUiState.copy(confirmPassword = event.confirmPassword)
            is RegisterEvent.RegisterClicked -> performRegister()
            is RegisterEvent.ErrorDismissed -> registerUiState = registerUiState.copy(errorMessage = null)
        }
    }

    private fun performRegister() {
        val s = registerUiState

        // Validasi
        if (s.name.isBlank() || s.email.isBlank() || s.password.isBlank()) {
            registerUiState = registerUiState.copy(errorMessage = "Nama, Email, dan Password wajib diisi")
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

        // Mulai Loading
        registerUiState = registerUiState.copy(isLoading = true, errorMessage = null)

        auth.createUserWithEmailAndPassword(s.email, s.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        val userMap = hashMapOf(
                            "name" to s.name,
                            "email" to s.email,
                            "phone" to s.phone,
                            "address" to s.address,
                            "role" to "customer"
                        )

                        db.collection("users").document(userId).set(userMap)
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
        registerUiState = RegisterUiState()
    }
}
