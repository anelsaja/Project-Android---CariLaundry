package com.example.carilaundry.domain.model

data class OwnerOrderDetail(
    val id: String,
    val customerName: String,
    val service: String,
    val category: String,
    val duration: String,
    val weightKg: Int,
    val notes: String,
    val pickupMethod: String,
    val time: String,
    val address: String,
    val paymentMethod: String,
    val subtotalText: String,
    val deliveryFeeText: String,
    val totalText: String,
)