package com.example.carilaundry.ui.feature.customer.favorite

import com.example.carilaundry.domain.model.FavoriteLaundry

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val favoriteList: List<FavoriteLaundry> = emptyList(),
    val errorMessage: String? = null
)