package com.example.carilaundry.ui.feature.customer.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.R
import com.example.carilaundry.domain.model.FavoriteLaundry
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState: StateFlow<FavoriteUiState> = _uiState.asStateFlow()

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch {
            // 1. Set Loading
            _uiState.update { it.copy(isLoading = true) }

            // Simulasi delay network/database (agar terlihat loadingnya)
            delay(1000)

            // 2. Data Dummy (Nanti diganti dengan panggilan Repository/Database)
            val dummyData = listOf(
                FavoriteLaundry("1", "Laundry Wertwer", "Jalan Senopati No. 3", "+62 813-2707-4781", R.drawable.icon),
                FavoriteLaundry("2", "Laundry Bersih", "Jalan Mawar No. 10", "+62 812-3333-4444", R.drawable.icon),
                FavoriteLaundry("3", "Cuci Kilat", "Jalan Melati No. 5", "+62 811-5555-6666", R.drawable.icon)
            )

            // 3. Update UI dengan data sukses
            _uiState.update {
                it.copy(
                    isLoading = false,
                    favoriteList = dummyData
                )
            }
        }
    }
}