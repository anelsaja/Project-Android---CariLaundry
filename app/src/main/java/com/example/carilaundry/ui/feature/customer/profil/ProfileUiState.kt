package com.example.carilaundry.ui.feature.customer.profil

import com.example.carilaundry.domain.model.UserProfile

data class ProfileUiState(
    val isLoading: Boolean = false,
    val userProfile: UserProfile? = null,
    val errorMessage: String? = null
)