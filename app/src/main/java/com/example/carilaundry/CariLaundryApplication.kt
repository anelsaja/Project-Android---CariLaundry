package com.example.carilaundry

import android.app.Application

class CariLaundryApplication : Application() {

    // Referensi ke AppContainer
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // Langsung inisialisasi class AppContainer
        container = AppContainer()
    }
}