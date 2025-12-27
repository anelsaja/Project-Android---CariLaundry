package com.example.carilaundry.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// --- IMPORT COMPONENT ---
import com.example.carilaundry.ui.components.BottomNavBar

// --- AUTH ---
import com.example.carilaundry.ui.feature.auth.RoleSelectionScreen

// --- CUSTOMER IMPORTS ---
import com.example.carilaundry.ui.feature.customer.login.CustomerLoginScreen
import com.example.carilaundry.ui.feature.customer.register.CustomerRegisterScreen
import com.example.carilaundry.ui.feature.customer.home.CustomerHomeScreen
import com.example.carilaundry.ui.feature.customer.deskripsi.DeskripsiScreen
import com.example.carilaundry.ui.feature.customer.orders.CustomerOrdersScreen
import com.example.carilaundry.ui.feature.customer.detail_pesanan.DetailPesananCustomerScreen
import com.example.carilaundry.ui.feature.customer.order_success.OrderSuccessScreen
import com.example.carilaundry.ui.feature.customer.favorite.CustomerFavoriteScreen
import com.example.carilaundry.ui.feature.customer.notifikasi.NotifikasiCustomerScreen
import com.example.carilaundry.ui.feature.customer.profil.ProfilScreen

// --- OWNER IMPORTS ---
import com.example.carilaundry.ui.feature.owner.login.OwnerLoginScreen
import com.example.carilaundry.ui.feature.owner.register.OwnerRegisterScreen
import com.example.carilaundry.ui.feature.owner.pesanan.OwnerOrdersScreen
import com.example.carilaundry.ui.feature.owner.notifikasi.NotifikasiOwnerScreen
import com.example.carilaundry.ui.feature.owner.profil.OwnerProfilScreen
import com.example.carilaundry.ui.feature.owner.detail_pesanan.DetailPesananScreen as DetailPesananOwnerScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // DAFTAR ROUTE YANG MENAMPILKAN BOTTOM NAV BAR
    val bottomRoutes = setOf(
        "customer/home",
        "customer/orders",
        "customer/profile"
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute in bottomRoutes) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding: PaddingValues ->
        NavHost(
            navController = navController,
            startDestination = "customer/login",
            modifier = Modifier.padding(innerPadding)
        ) {

            // ================= AUTH =================
            composable("customer/login") {
                CustomerLoginScreen(
                    onLoginSuccess = {
                        // Jika login sukses (dari ViewModel), pindah ke Home
                        navController.navigate("customer/home") {
                            popUpTo("customer/login") { inclusive = true }
                        }
                    },
                    onRegisterClicked = { navController.navigate("customer/register") },
                    onSwitchToOwner = { navController.navigate("owner/login") }
                )
            }

            composable("owner/login") {
                OwnerLoginScreen(
                    onLoginSuccess = {
                        // Pindah ke Home Owner
                        navController.navigate("owner/home") {
                            popUpTo("owner/login") { inclusive = true }
                        }
                    },
                    onRegisterClicked = { navController.navigate("owner/register") },
                    onSwitchToCustomer = { navController.navigate("customer/login") }
                )
            }

            composable("roleSelection") {
                RoleSelectionScreen(
                    onSelectCustomer = { navController.navigate("customer/register") },
                    onSelectOwner = { navController.navigate("owner/register") },
                    onSignInClicked = { navController.navigate("customer/login") }
                )
            }

            composable("customer/register") {
                CustomerRegisterScreen(
                    onRegisterSuccess = {
                        // Setelah daftar, bisa ke login atau langsung home
                        navController.navigate("customer/login") {
                            popUpTo("customer/register") { inclusive = true }
                        }
                    },
                    onLoginClicked = { navController.popBackStack() }
                )
            }

            composable("owner/register") {
                OwnerRegisterScreen(
                    onRegisterSuccess = {
                        // Sukses daftar -> Pindah ke Home Owner
                        navController.navigate("owner/home") {
                            popUpTo("owner/register") { inclusive = true }
                        }
                    },
                    onSignInClicked = { navController.popBackStack() }
                )
            }

            // ================= CUSTOMER FEATURES =================
            composable("customer/home") {
                CustomerHomeScreen(
                    onItemClick = { id -> navController.navigate("customer/deskripsi/$id") },
                    onOpenFavorites = { navController.navigate("customer/favorites") },
                    onOpenNotifications = { navController.navigate("customer/notifications") },
                    onOpenProfile = { navController.navigate("customer/profile") }
                )
            }

            composable("customer/notifications") {
                NotifikasiCustomerScreen(
                    onBack = { navController.popBackStack() },
                    onDetailClick = { id -> navController.navigate("customer/detail_pesanan/$id") }
                )
            }

            composable("customer/orders") {
                CustomerOrdersScreen(
                    onBack = { navController.popBackStack() },
                    onOpenOrder = { orderId ->
                        // Navigasi ke detail pesanan (Progress pengerjaan)
                        navController.navigate("customer/detail_pesanan/$orderId")
                    }
                )
            }

            composable("customer/profile") {
                ProfilScreen(
                    onBack = { navController.popBackStack() },
                    onLogout = {
                        navController.navigate("customer/login") {
                            popUpTo("customer/home") { inclusive = true }
                        }
                    }
                )
            }

            // === UPDATE PENTING: DeskripsiScreen menggunakan parameter {laundryId} ===
            composable(
                route = "customer/deskripsi/{laundryId}",
                arguments = listOf(navArgument("laundryId") { type = NavType.StringType })
            ) { backStackEntry ->

                // Kita ambil ID hanya untuk diteruskan ke tombol "Pesan Sekarang"
                val currentId = backStackEntry.arguments?.getString("laundryId") ?: ""

                DeskripsiScreen(
                    // Parameter 'laundryId' dihapus karena sudah di-inject via ViewModel
                    onBack = { navController.popBackStack() },
                    onOrderNow = {
                        navController.navigate("customer/detail_pesanan/$currentId")
                    },
                    onOpenMap = {}
                )
            }

            composable("customer/detail_pesanan/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")

                DetailPesananCustomerScreen(
                    // Hapus parameter laundryId jika merah (karena sudah di-handle ViewModel)
                    // laundryId = id,

                    onBack = { navController.popBackStack() },

                    // UBAH INI: dari onPlaceOrder menjadi onSubmitOrder
                    onSubmitOrder = {
                        navController.navigate("customer/order_success/$id")
                    }
                )
            }

            composable("customer/order_success?name={name}&address={address}",
                arguments = listOf(
                    navArgument("name") { defaultValue = "Laundry" },
                    navArgument("address") { defaultValue = "-" }
                )
            ) {
                OrderSuccessScreen(
                    onOk = {
                        // Kembali ke Home dan hapus semua backstack order
                        navController.navigate("customer/home") {
                            popUpTo("customer/home") { inclusive = true }
                        }
                    }
                )
            }

            composable("customer/favorites") {
                CustomerFavoriteScreen(
                    onBack = { navController.popBackStack() },
                    onItemClick = { id ->
                        navController.navigate("customer/deskripsi/$id")
                    }
                )
            }

            // ================= OWNER FEATURES =================

            composable("owner/home") {
                OwnerOrdersScreen(
                    onDetailClick = { order ->
                        // Navigasi ke Detail Pesanan Owner (akan kita buat nanti)
                        navController.navigate("owner/detail_pesanan/${order.id}")
                    },
                    onOpenNotifications = {
                        // Navigasi ke Notifikasi Owner (akan kita buat nanti)
                        navController.navigate("owner/notifications")
                    },
                    onOpenProfile = {
                        // Navigasi ke Profil Owner (akan kita buat nanti)
                        navController.navigate("owner/profile")
                    }
                )
            }

            composable("owner/notifications") {
                NotifikasiOwnerScreen(
                    onBack = { navController.popBackStack() },
                    onDetailClick = { id ->
                        navController.navigate("owner/detail_pesanan/$id")
                    }
                )
            }

            composable("owner/detail_pesanan/{id}") {
                DetailPesananOwnerScreen(
                    onBack = { navController.popBackStack() },
                    onProcessPickup = { /* Logika update status */ },
                    onProcessProgress = { /* Logika update status */ },
                    onComplete = { navController.popBackStack() }
                )
            }

            composable("owner/profile") {
                OwnerProfilScreen(
                    onBack = { navController.popBackStack() },
                    onLogout = {
                        navController.navigate("owner/login") {
                            popUpTo("owner/home") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}