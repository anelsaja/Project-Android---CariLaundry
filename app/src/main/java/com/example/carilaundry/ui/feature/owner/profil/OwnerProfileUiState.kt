package com.example.carilaundry.ui.feature.owner.profil

import com.example.carilaundry.domain.model.OwnerProfile

data class OwnerProfileUiState(
    val isLoading: Boolean = false,
    val ownerProfile: OwnerProfile? = null,
    val errorMessage: String? = null
)