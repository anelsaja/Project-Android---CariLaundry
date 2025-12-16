package com.example.carilaundry.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

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
import com.example.carilaundry.ui.feature.owner.detail_pesanan.DetailPesananScreen as DetailPesananOwnerScreen // Alias biar gak bentrok nama

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // DAFTAR ROUTE YANG MENAMPILKAN BOTTOM NAV BAR
    // (Hanya sisi Customer yang pakai Navbar)
    val bottomRoutes = setOf(
        "customer/home",
        "customer/orders",
        "customer/profile"
        // Route Owner DIHAPUS dari sini agar Navbar hilang
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
                    onLogin = { email, password ->
                        if (email == "customer" && password == "123")
                            navController.navigate("customer/home")
                    },
                    // PERUBAHAN: Langsung ke Customer Register
                    onRegisterClicked = { navController.navigate("customer/register") },
                    onSwitchToOwner = { navController.navigate("owner/login") }
                )
            }

            composable("owner/login") {
                OwnerLoginScreen(
                    onLogin = { email, password ->
                        if (email == "owner" && password == "123") {
                            navController.navigate("owner/home")
                        }
                    },
                    // PERUBAHAN: Langsung ke Owner Register
                    onRegisterClicked = { navController.navigate("owner/register") },
                    onSwitchToCustomer = { navController.navigate("customer/login") }
                )
            }

            // Route ini mungkin tidak terpakai lagi via tombol register,
            // tapi dibiarkan saja jika nanti dibutuhkan.
            composable("roleSelection") {
                RoleSelectionScreen(
                    onSelectCustomer = { navController.navigate("customer/register") },
                    onSelectOwner = { navController.navigate("owner/register") },
                    onSignInClicked = { navController.navigate("customer/login") }
                )
            }

            composable("customer/register") {
                CustomerRegisterScreen(
                    onRegister = { name, email, password ->
                        navController.navigate("customer/home") {
                            popUpTo("customer/login") { inclusive = true }
                        }
                    },
                    onLoginClicked = { navController.popBackStack() }
                )
            }

            composable("owner/register") {
                OwnerRegisterScreen(
                    onRegister = { businessName, ownerName, email, phone, address, password ->
                        navController.navigate("owner/home") {
                            popUpTo("owner/login") { inclusive = true }
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
                    onOpenOrder = { orderId -> /* Handle detail order */ }
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

            composable("customer/deskripsi/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                DeskripsiScreen(
                    laundryId = id,
                    onBack = { navController.popBackStack() },
                    onOrderNow = { navController.navigate("customer/detail_pesanan/$id") },
                    onOpenMap = {}
                )
            }

            composable("customer/detail_pesanan/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                DetailPesananCustomerScreen(
                    laundryId = id,
                    onBack = { navController.popBackStack() },
                    onPlaceOrder = { navController.navigate("customer/order_success/$id") }
                )
            }

            composable("customer/order_success/{id}") { backStackEntry ->
                OrderSuccessScreen(
                    onBack = { navController.popBackStack() },
                    onOk = {
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

            // ================= OWNER FEATURES (NO BOTTOM BAR) =================

            // 1. HOME OWNER (List Pesanan)
            composable("owner/home") {
                OwnerOrdersScreen(
                    onDetailClick = { order ->
                        // Navigasi ke Detail Pesanan Owner
                        navController.navigate("owner/detail_pesanan/${order.id}")
                    },
                    onOpenNotifications = {
                        navController.navigate("owner/notifications")
                    },
                    onOpenProfile = {
                        navController.navigate("owner/profile")
                    }
                )
            }

            // 2. NOTIFIKASI OWNER
            composable("owner/notifications") {
                NotifikasiOwnerScreen(
                    onBack = { navController.popBackStack() },
                    onDetailClick = { id ->
                        // Jika notif diklik, lari ke detail pesanan juga
                        navController.navigate("owner/detail_pesanan/$id")
                    }
                )
            }

            // 3. DETAIL PESANAN OWNER
            composable("owner/detail_pesanan/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                // Menggunakan alias DetailPesananOwnerScreen agar tidak tertukar dengan customer
                DetailPesananOwnerScreen(
                    onBack = { navController.popBackStack() },
                    onProcessPickup = { /* Logika update status */ },
                    onProcessProgress = { /* Logika update status */ },
                    onComplete = { navController.popBackStack() } // Selesai kembali ke list
                )
            }

            // 4. PROFILE OWNER
            composable("owner/profile") {
                OwnerProfilScreen(
                    onBack = { navController.popBackStack() },
                    onLogout = {
                        // Logout: Kembali ke Login Owner dan bersihkan history
                        navController.navigate("owner/login") {
                            popUpTo("owner/home") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}