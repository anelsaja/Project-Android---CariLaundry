package com.example.carilaundry.ui.feature.customer.profil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val currentUser = auth.currentUser
            if (currentUser != null) {
                try {
                    // Ambil data user dari Firestore collection 'users'
                    val documentSnapshot = firestore.collection("users")
                        .document(currentUser.uid)
                        .get()
                        .await()

                    if (documentSnapshot.exists()) {
                        val name = documentSnapshot.getString("name") ?: "No Name"
                        val email = currentUser.email ?: "No Email"
                        val phone = documentSnapshot.getString("phone") ?: "No Phone"
                        val address = documentSnapshot.getString("address") ?: "No Address"
                        val initials = getInitials(name)

                        val userProfile = UserProfile(
                            name = name,
                            email = email,
                            phone = phone,
                            address = address,
                            initials = initials
                        )

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                userProfile = userProfile
                            )
                        }
                    } else {
                        // User login tapi data belum ada di Firestore
                        // Bisa tampilkan data basic dari Auth saja
                         val userProfile = UserProfile(
                            name = currentUser.displayName ?: "User",
                            email = currentUser.email ?: "",
                            phone = currentUser.phoneNumber ?: "",
                            address = "-",
                            initials = getInitials(currentUser.displayName ?: "User")
                        )
                        _uiState.update { it.copy(isLoading = false, userProfile = userProfile) }
                    }
                } catch (e: Exception) {
                    // Handle error, misalnya koneksi gagal
                     _uiState.update { it.copy(isLoading = false) }
                     e.printStackTrace()
                }
            } else {
                // User belum login
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun getInitials(name: String): String {
        val words = name.trim().split(" ")
        return when {
            words.isEmpty() -> ""
            words.size == 1 -> words[0].take(2).uppercase()
            else -> "${words[0].first()}${words[1].first()}".uppercase()
        }
    }

    fun logout() {
        auth.signOut()
        _uiState.update { it.copy(userProfile = null) }
    }
}
