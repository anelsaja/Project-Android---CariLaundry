package com.example.carilaundry.ui.feature.owner.profil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.model.OwnerProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OwnerProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OwnerProfileUiState())
    val uiState: StateFlow<OwnerProfileUiState> = _uiState.asStateFlow()

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val currentUser = auth.currentUser
            if (currentUser != null) {
                try {
                    // Ambil data dari koleksi 'owners' berdasarkan UID
                    val documentSnapshot = firestore.collection("owners")
                        .document(currentUser.uid)
                        .get()
                        .await()

                    if (documentSnapshot.exists()) {
                        val name = documentSnapshot.getString("ownerName") ?: "No Name"
                        // Jika di register fieldnya 'businessName', bisa diambil juga jika perlu
                        val email = currentUser.email ?: "No Email"
                        val phone = documentSnapshot.getString("phone") ?: "No Phone"
                        val address = documentSnapshot.getString("address") ?: "No Address"
                        
                        val initials = getInitials(name)

                        val profile = OwnerProfile(
                            name = name,
                            role = "Pemilik Laundry", // Role statis atau ambil dari DB
                            email = email,
                            phone = phone,
                            address = address,
                            initials = initials
                        )

                        _uiState.update {
                            it.copy(isLoading = false, ownerProfile = profile)
                        }
                    } else {
                        // Jika data tidak ada di Firestore, pakai data Auth seadanya
                        val profile = OwnerProfile(
                            name = currentUser.displayName ?: "Owner",
                            role = "Pemilik Laundry",
                            email = currentUser.email ?: "",
                            phone = currentUser.phoneNumber ?: "-",
                            address = "-",
                            initials = getInitials(currentUser.displayName ?: "Owner")
                        )
                         _uiState.update { it.copy(isLoading = false, ownerProfile = profile) }
                    }

                } catch (e: Exception) {
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
        _uiState.update { it.copy(ownerProfile = null) }
    }
}
