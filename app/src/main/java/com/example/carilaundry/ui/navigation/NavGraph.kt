package com.example.carilaundry.ui.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// --- DOMAIN MODELS IMPORT ---
import com.example.carilaundry.domain.model.OwnerOrder

// --- FIREBASE IMPORTS ---
import com.google.firebase.auth.FirebaseAuth

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
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    // DAFTAR ROUTE YANG MENAMPILKAN BOTTOM NAV BAR
    val bottomRoutes = setOf(
        "customer/home",
        "customer/orders",
        "customer/profile",
        "owner/home",
        "owner/profile"
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute in bottomRoutes) {
                if (currentRoute?.startsWith("customer") == true) {
                     BottomNavBar(navController = navController)
                }
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
                        navController.navigate("customer/home") {
                            popUpTo("customer/login") { inclusive = true }
                        }
                        Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    },
                    onNavigateToRegister = { navController.navigate("customer/register") },
                    onNavigateToOwner = { navController.navigate("owner/login") }
                )
            }

            composable("owner/login") {
                OwnerLoginScreen(
                    onLoginSuccess = {
                        navController.navigate("owner/home") {
                            popUpTo("owner/login") { inclusive = true }
                        }
                        Toast.makeText(context, "Login Owner Berhasil", Toast.LENGTH_SHORT).show()
                    },
                    onNavigateToRegister = { navController.navigate("owner/register") },
                    onNavigateToCustomer = { navController.navigate("customer/login") }
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
                        navController.navigate("customer/home") {
                            popUpTo("customer/login") { inclusive = true }
                        }
                        Toast.makeText(context, "Register Berhasil", Toast.LENGTH_SHORT).show()
                    },
                    onNavigateToLogin = { navController.popBackStack() }
                )
            }

            composable("owner/register") {
                OwnerRegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate("owner/home") {
                            popUpTo("owner/login") { inclusive = true }
                        }
                        Toast.makeText(context, "Register Owner Berhasil", Toast.LENGTH_SHORT).show()
                    },
                    onNavigateToLogin = { navController.popBackStack() }
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
                    onDetailClick = { id -> navController.navigate("customer/orders") }
                )
            }

            composable("customer/orders") {
                CustomerOrdersScreen(
                    onBack = { navController.popBackStack() },
                    onNavigateToDetail = { /* Nanti bisa handle detail order spesifik jika ada */ }
                )
            }

            composable("customer/profile") {
                ProfilScreen(
                    onBack = { navController.popBackStack() },
                    onLogout = {
                        auth.signOut()
                        navController.navigate("customer/login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            composable(
                route = "customer/deskripsi/{laundryId}",
                arguments = listOf(navArgument("laundryId") { type = NavType.StringType })
            ) { backStackEntry ->
                val currentId = backStackEntry.arguments?.getString("laundryId") ?: ""
                DeskripsiScreen(
                    onBack = { navController.popBackStack() },
                    onOrderNow = { navController.navigate("customer/detail_pesanan/$currentId") },
                    onOpenMap = {}
                )
            }

            composable(
                route = "customer/detail_pesanan/{laundryId}",
                arguments = listOf(navArgument("laundryId") { type = NavType.StringType })
            ) { backStackEntry ->
                val laundryId = backStackEntry.arguments?.getString("laundryId")

                DetailPesananCustomerScreen(
                    onBack = { navController.popBackStack() },
                    onSubmitOrder = {
                        navController.navigate("customer/order_success/$laundryId")
                    }
                )
            }

            composable("customer/order_success/{id}") {
                OrderSuccessScreen(
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
                    onItemClick = { id -> navController.navigate("customer/deskripsi/$id") }
                )
            }

            // ================= OWNER FEATURES =================

            composable("owner/home") {
                OwnerOrdersScreen(
                    onDetailClick = { order ->
                        navController.navigate("owner/detail_pesanan/${order.id}")
                    },
                    onOpenNotifications = { navController.navigate("owner/notifications") },
                    onOpenProfile = { navController.navigate("owner/profile") }
                )
            }

            composable("owner/notifications") {
                NotifikasiOwnerScreen(
                    onBack = { navController.popBackStack() },
                    onDetailClick = { id -> navController.navigate("owner/detail_pesanan/$id") }
                )
            }

            // UPDATE: Menambahkan definisi arguments agar ID terbaca dengan benar
            composable(
                route = "owner/detail_pesanan/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                
                DetailPesananOwnerScreen(
                    onBack = { navController.popBackStack() },
                    onProcessPickup = { orderId -> /* Logika update status */ },
                    onProcessProgress = { orderId -> /* Logika update status */ },
                    onComplete = { orderId -> 
                        navController.popBackStack() 
                    }
                )
            }

            composable("owner/profile") {
                OwnerProfilScreen(
                    onBack = { navController.popBackStack() },
                    onLogout = {
                        auth.signOut()
                        navController.navigate("owner/login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
