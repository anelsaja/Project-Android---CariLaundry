package com.example.carilaundry.ui.feature.customer.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CustomerLoginViewModel : ViewModel() {

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                loginUiState = loginUiState.copy(email = event.email)
            }
            is LoginEvent.PasswordChanged -> {
                loginUiState = loginUiState.copy(password = event.password)
            }
            is LoginEvent.LoginClicked -> {
                performLogin()
            }
            is LoginEvent.ErrorDismissed -> {
                loginUiState = loginUiState.copy(errorMessage = null)
            }
        }
    }

    private fun performLogin() {
        val email = loginUiState.email
        val password = loginUiState.password

        if (email.isBlank() || password.isBlank()) {
            loginUiState = loginUiState.copy(errorMessage = "Email dan Password harus diisi")
            return
        }

        loginUiState = loginUiState.copy(isLoading = true, errorMessage = null)

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = task.result.user?.uid
                    if (uid != null) {
                        checkRole(uid)
                    } else {
                        loginUiState = loginUiState.copy(isLoading = false, errorMessage = "User ID tidak ditemukan")
                    }
                } else {
                    loginUiState = loginUiState.copy(
                        isLoading = false,
                        isSuccess = false,
                        errorMessage = task.exception?.message ?: "Login Gagal"
                    )
                }
            }
    }

    private fun checkRole(uid: String) {
        // Cek apakah UID ada di koleksi 'customers'
        firestore.collection("customers").document(uid).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Valid: User adalah Customer
                    loginUiState = loginUiState.copy(
                        isLoading = false,
                        isSuccess = true,
                        errorMessage = null
                    )
                } else {
                    // Invalid: User login tapi bukan Customer (Mungkin Owner)
                    auth.signOut() // Logout paksa
                    loginUiState = loginUiState.copy(
                        isLoading = false,
                        isSuccess = false,
                        errorMessage = "Akun Anda tidak terdaftar sebagai Customer. Coba login di Owner."
                    )
                }
            }
            .addOnFailureListener { e ->
                auth.signOut()
                loginUiState = loginUiState.copy(
                    isLoading = false,
                    isSuccess = false,
                    errorMessage = "Gagal memverifikasi role: ${e.message}"
                )
            }
    }

    fun resetState() {
        loginUiState = LoginUiState()
    }
}
