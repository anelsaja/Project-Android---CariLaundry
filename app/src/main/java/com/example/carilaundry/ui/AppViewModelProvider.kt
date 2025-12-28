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
import com.example.carilaundry.ui.feature.owner.detail_pesanan.DetailOwnerViewModel
import com.example.carilaundry.ui.feature.customer.orders.CustomerOrdersViewModel
import com.example.carilaundry.ui.feature.owner.pesanan.OwnerOrdersViewModel
import com.example.carilaundry.ui.feature.owner.profil.OwnerProfileViewModel
import com.example.carilaundry.ui.feature.customer.order_success.OrderSuccessViewModel
import com.example.carilaundry.ui.feature.customer.notifikasi.NotificationViewModel
// Perbaiki import untuk Owner Notification ViewModel
import com.example.carilaundry.ui.feature.owner.notifikasi.OwnerNotificationViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer untuk HomeViewModel
        initializer {
//            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CariLaundryApplication)
            HomeViewModel()
        }

        // Initializer untuk DetailViewModel (Deskripsi Laundry Customer)
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CariLaundryApplication)
            DetailViewModel(
                this.createSavedStateHandle(),
                application.container.laundryRepository
            )
        }

        // Initializer untuk OrderViewModel (Form Pemesanan Customer)
        initializer {
            val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CariLaundryApplication)
            OrderViewModel(
                this.createSavedStateHandle(),
                application.container.laundryRepository
            )
        }
        
        // Initializer untuk OrderSuccessViewModel
        initializer {
             OrderSuccessViewModel(this.createSavedStateHandle())
        }
        
        // Initializer untuk CustomerOrdersViewModel (Daftar Pesanan Customer)
        initializer {
            CustomerOrdersViewModel()
        }
        
        // Initializer untuk NotificationViewModel (Customer)
        initializer {
            NotificationViewModel()
        }

        // --- OWNER VIEWMODELS ---

        // Initializer untuk OwnerOrdersViewModel (Daftar Pesanan Masuk Owner)
        initializer {
            OwnerOrdersViewModel()
        }

        // Initializer untuk DetailOwnerViewModel (Detail Pesanan Owner)
        initializer {
            DetailOwnerViewModel(
                this.createSavedStateHandle()
            )
        }

        // Initializer untuk OwnerProfileViewModel
        initializer {
            OwnerProfileViewModel()
        }
        
        // Initializer untuk OwnerNotificationViewModel (Nama yang benar)
        initializer {
            OwnerNotificationViewModel()
        }
    }
}
