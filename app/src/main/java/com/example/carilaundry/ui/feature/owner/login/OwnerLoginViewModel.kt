package com.example.carilaundry.ui.feature.owner.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OwnerLoginViewModel : ViewModel() {

    var loginUiState by mutableStateOf(OwnerLoginUiState())
        private set

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun onEvent(event: OwnerLoginEvent) {
        when (event) {
            is OwnerLoginEvent.EmailChanged -> {
                loginUiState = loginUiState.copy(email = event.email)
            }
            is OwnerLoginEvent.PasswordChanged -> {
                loginUiState = loginUiState.copy(password = event.password)
            }
            is OwnerLoginEvent.LoginClicked -> {
                performLogin()
            }
            is OwnerLoginEvent.ErrorDismissed -> {
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
        // Cek apakah UID ada di koleksi 'owners'
        firestore.collection("owners").document(uid).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Valid: User adalah Owner
                    loginUiState = loginUiState.copy(
                        isLoading = false,
                        isSuccess = true,
                        errorMessage = null
                    )
                } else {
                    // Invalid: User login tapi bukan Owner (Mungkin Customer)
                    auth.signOut() // Logout paksa
                    loginUiState = loginUiState.copy(
                        isLoading = false,
                        isSuccess = false,
                        errorMessage = "Akun Anda tidak terdaftar sebagai Owner. Coba login di Customer."
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
        loginUiState = OwnerLoginUiState()
    }
}
