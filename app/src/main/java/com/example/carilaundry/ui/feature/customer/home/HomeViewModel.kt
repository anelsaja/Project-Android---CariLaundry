package com.example.carilaundry.ui.feature.customer.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.model.Laundry
import com.example.carilaundry.domain.usecase.GetLaundryListUseCase
import kotlinx.coroutines.launch
import java.io.IOException

// Status tampilan (Loading, Success, Error)
sealed interface HomeUiState {
    data class Success(val laundryList: List<Laundry>) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}

class HomeViewModel(
    private val getLaundryListUseCase: GetLaundryListUseCase
) : ViewModel() {

    // State yang akan diamati oleh UI
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getLaundryList()
    }

    fun getLaundryList() {
        viewModelScope.launch {
            homeUiState = HomeUiState.Loading
            try {
                // Panggil UseCase (Dummy Data akan langsung masuk sini)
                val list = getLaundryListUseCase()
                homeUiState = HomeUiState.Success(list)
            } catch (e: IOException) {
                homeUiState = HomeUiState.Error
            }
        }
    }
}