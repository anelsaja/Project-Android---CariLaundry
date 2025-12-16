package com.example.carilaundry

import com.example.carilaundry.data.repository.LaundryRepositoryImpl
import com.example.carilaundry.domain.repository.LaundryRepository
import com.example.carilaundry.domain.usecase.GetLaundryListUseCase

// Hapus import Retrofit karena kita pakai Dummy Data dulu
// import com.example.carilaundry.data.remote.LaundryApiService
// import retrofit2.Retrofit
// import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {

    // --- BAGIAN RETROFIT (DI-SKIP DULU) ---
    // private val retrofit = Retrofit.Builder() ...
    // private val apiService = retrofit.create(...)

    // --- BAGIAN REPOSITORY ---

    // Karena LaundryRepositoryImpl kamu pakai dummy data dan tidak butuh 'apiService',
    // cukup inisialisasi kosong saja: LaundryRepositoryImpl()
    val laundryRepository: LaundryRepository = LaundryRepositoryImpl()

    // --- BAGIAN USECASE ---
    val getLaundryListUseCase = GetLaundryListUseCase(laundryRepository)
}