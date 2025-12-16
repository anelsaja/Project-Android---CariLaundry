package com.example.carilaundry.domain.repository

import com.example.carilaundry.domain.model.Laundry

interface LaundryRepository {
    fun getLaundries(): List<Laundry>
    fun getLaundryById(id: String): Laundry?
}