package com.example.carilaundry.ui.feature.owner.notifikasi

import com.example.carilaundry.domain.model.OwnerNotificationItem

data class OwnerNotificationUiState(
    val isLoading: Boolean = false,
    val notificationList: List<OwnerNotificationItem> = emptyList(),
    val errorMessage: String? = null
)