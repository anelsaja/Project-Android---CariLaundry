package com.example.carilaundry.domain.model

data class CustomerOrder(
    val id: String,
    val service: String,
    val weight: String,
    val total: String,
    val status: String,
    val estimation: String
)