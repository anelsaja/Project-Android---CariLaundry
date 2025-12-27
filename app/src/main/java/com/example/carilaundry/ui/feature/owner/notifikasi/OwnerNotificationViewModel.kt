package com.example.carilaundry.ui.feature.owner.notifikasi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.model.OwnerNotificationItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OwnerNotificationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OwnerNotificationUiState())
    val uiState: StateFlow<OwnerNotificationUiState> = _uiState.asStateFlow()

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            // 1. Loading
            _uiState.update { it.copy(isLoading = true) }

            // Simulasi Network
            delay(1000)

            // 2. Data Dummy
            val dummyList = List(6) {
                OwnerNotificationItem(
                    id = it.toString(),
                    customerName = "Mas Anel",
                    address = "Jalan Senopati No. 3, Kampungin",
                    message = "Pesanan Baru Masuk: Cuci Komplit 3kg",
                    time = "${it + 1} jam yang lalu"
                )
            }

            // 3. Success
            _uiState.update {
                it.copy(
                    isLoading = false,
                    notificationList = dummyList
                )
            }
        }
    }
}