package com.example.carilaundry.domain.model

import androidx.annotation.DrawableRes

data class Laundry(
    val id: String,
    val name: String,
    val address: String,
    val distance: String,
    @DrawableRes val imageRes: Int, // Anotasi ini agar aman saat isi ID gambar

    // Tambahan untuk Detail Screen (opsional, bisa diisi default value dulu)
    val rating: Double = 0.0,
    val phone: String = "",
    val priceStart: String = ""
)