package com.example.carilaundry.ui.feature.customer.notifikasi

import com.example.carilaundry.domain.model.NotificationItem

data class NotificationUiState(
    val isLoading: Boolean = false,
    val notificationList: List<NotificationItem> = emptyList(),
    val errorMessage: String? = null
)