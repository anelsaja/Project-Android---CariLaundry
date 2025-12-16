package com.example.carilaundry.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.carilaundry.CariLaundryApplication
import com.example.carilaundry.ui.feature.customer.deskripsi.DetailViewModel
import com.example.carilaundry.ui.feature.customer.detail_pesanan.OrderViewModel
import com.example.carilaundry.ui.feature.customer.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer untuk HomeViewModel
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CariLaundryApplication)

            // Kita ambil useCase yang sudah disiapkan di AppContainer
            HomeViewModel(application.container.getLaundryListUseCase)
        }

        // TAMBAHKAN INI: Initializer untuk DetailViewModel
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CariLaundryApplication)

            DetailViewModel(
                this.createSavedStateHandle(), // PENTING: Untuk menangkap Nav Arguments
                application.container.laundryRepository // Kita akses repo langsung (tanpa UseCase khusus gpp untuk detail sederhana)
            )
        }

        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CariLaundryApplication)
            OrderViewModel(
                this.createSavedStateHandle(),
                application.container.laundryRepository
            )
        }

        // Nanti kalau ada ViewModel lain (misal DetailViewModel), tambahkan di bawah sini
    }
}