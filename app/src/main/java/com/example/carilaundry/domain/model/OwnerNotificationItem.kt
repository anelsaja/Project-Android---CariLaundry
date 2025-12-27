package com.example.carilaundry.domain.model

data class OwnerNotificationItem(
    val id: String,
    val customerName: String,
    val address: String,
    val message: String,
    val time: String
)