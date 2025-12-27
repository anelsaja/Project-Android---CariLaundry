package com.example.carilaundry.domain.model

data class OwnerOrder(
    val id: String,
    val customerName: String,
    val address: String,
    val service: String,
    val weightEstimate: String,
    val priceText: String
)