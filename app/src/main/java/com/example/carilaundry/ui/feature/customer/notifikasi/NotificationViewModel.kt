package com.example.carilaundry.ui.feature.customer.notifikasi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carilaundry.domain.model.NotificationItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotificationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    init {
        getNotifications()
    }

    private fun getNotifications() {
        viewModelScope.launch {
            // 1. Set Loading
            _uiState.update { it.copy(isLoading = true) }

            // Simulasi loading network
            delay(1000)

            // 2. Data Dummy
            val dummyList = List(6) {
                NotificationItem(
                    id = it.toString(),
                    name = "Mas Anel",
                    address = "Jalan Senopati No. 3, Kampungin",
                    message = "Melakukan Orderan Pada Laundry xxx",
                    time = "2 jam yang lalu"
                )
            }

            // 3. Update UI Success
            _uiState.update {
                it.copy(
                    isLoading = false,
                    notificationList = dummyList
                )
            }
        }
    }
}