package com.example.carilaundry.data.repository

import com.example.carilaundry.R
import com.example.carilaundry.domain.model.Laundry
import com.example.carilaundry.domain.repository.LaundryRepository

class LaundryRepositoryImpl : LaundryRepository {

    // Pindahkan dummy data dari UI ke sini
    private val dummyData = listOf(
        Laundry("1", "Laundry Wertwer", "Jalan Senopati No. 3", "135 m", R.drawable.icon),
        Laundry("2", "Laundry Bersih", "Jalan Mawar No. 10", "200 m", R.drawable.icon),
        Laundry("3", "Cuci Kilat", "Jalan Melati No. 5", "500 m", R.drawable.icon),
        Laundry("4", "Mama Laundry", "Jalan Anggrek No. 12", "1.2 km", R.drawable.icon)
    )

    override fun getLaundries(): List<Laundry> = dummyData

    override fun getLaundryById(id: String): Laundry? = dummyData.find { it.id == id }
}