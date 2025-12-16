package com.example.carilaundry.domain.usecase

import com.example.carilaundry.domain.model.Laundry
import com.example.carilaundry.domain.repository.LaundryRepository

class GetLaundryListUseCase(private val repository: LaundryRepository) {
    // Fungsi operator invoke memungkinkan kita memanggil kelas ini seperti fungsi biasa
    operator fun invoke(): List<Laundry> {
        return repository.getLaundries()
    }
}