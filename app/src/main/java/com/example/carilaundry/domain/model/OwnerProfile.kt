package com.example.carilaundry.domain.model

data class OwnerProfile(
    val name: String,
    val role: String, // Misal: "Pemilik Laundry"
    val email: String,
    val phone: String,
    val address: String,
    val initials: String // Misal: "PB"
)