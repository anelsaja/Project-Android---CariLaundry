package com.example.carilaundry.domain.model

data class UserProfile(
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val initials: String // Misal: "AS" untuk Anel Saja
)