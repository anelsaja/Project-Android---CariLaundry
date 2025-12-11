package com.example.carilaundry.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.carilaundry.ui.components.BottomNavBar
import com.example.carilaundry.ui.feature.auth.RoleSelectionScreen
import com.example.carilaundry.ui.feature.customer.login.CustomerLoginScreen
import com.example.carilaundry.ui.feature.customer.register.CustomerRegisterScreen
import com.example.carilaundry.ui.feature.customer.home.CustomerHomeScreen
import com.example.carilaundry.ui.feature.customer.deskripsi.DeskripsiScreen
import com.example.carilaundry.ui.feature.customer.orders.CustomerOrdersScreen
import com.example.carilaundry.ui.feature.owner.login.OwnerLoginScreen
import com.example.carilaundry.ui.feature.owner.register.OwnerRegisterScreen
import com.example.carilaundry.ui.feature.owner.pesanan.OwnerOrdersScreen
import com.example.carilaundry.ui.feature.customer.detail_pesanan.DetailPesananCustomerScreen
import com.example.carilaundry.ui.feature.customer.order_success.OrderSuccessScreen
import androidx.compose.material3.Text

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment

import com.example.carilaundry.ui.feature.customer.notifikasi.NotifikasiCustomerScreen
import com.example.carilaundry.ui.feature.customer.profil.ProfilScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val bottomRoutes = setOf(
        "customer/home",
        "customer/orders",
        "customer/profile",
        "owner/home",
        "owner/orders",
        "owner/profile"
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
            composable("customer/login") {
                CustomerLoginScreen(
                    onLogin = { email, password ->
                        if (email == "customer" && password == "123")
                            navController.navigate("customer/home")
                    },
                    onRegisterClicked = { navController.navigate("roleSelection") },
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
                    onRegisterClicked = { navController.navigate("roleSelection") },
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
                    onRegister = { name, email, phone, password ->
                        navController.navigate("customer/home") {
                            popUpTo("customer/login") { inclusive = true }
                        }
                    },
                    onSignInClicked = { navController.popBackStack() }
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

            composable("customer/home") {
                CustomerHomeScreen(
                    onItemClick = { id -> navController.navigate("customer/deskripsi/$id") },
                    onOpenFavorites = { navController.navigate("favorites") },
                    onOpenNotifications = { navController.navigate("notifications") },
                    onOpenProfile = { navController.navigate("customer/profile") }
                )
            }

            composable("notifications") {
                NotifikasiCustomerScreen(
                    onBack = { navController.popBackStack() },
                    onDetailClick = { id -> navController.navigate("customer/detail_pesanan/$id") }
                )
            }

            composable("customer/orders") {
                CustomerOrdersScreen(
                    onBack = { navController.popBackStack() },
                    onOpenOrder = { orderId -> /*  */ }
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
                    onPlaceOrder = { total -> navController.navigate("customer/order_success/$id") }
                )
            }

            composable("customer/order_success/{id}") { backStackEntry ->
                OrderSuccessScreen(
                    //id londri
                    onBack = { navController.popBackStack() },
                    onOk = { navController.navigate("customer/home") { popUpTo("customer/home") { inclusive = true } } }
                )
            }

            composable("owner/home") {
                OwnerOrdersScreen(
                    onDetailClick = { /*detail */ },
                    currentRoute = "home",
                    onNavigate = { shortRoute ->
                        when (shortRoute) {
                            "home" -> navController.navigate("owner/home")
                            "orders" -> navController.navigate("owner/orders")
                            "profile" -> navController.navigate("owner/profile")
                        }
                    }
                )
            }

            composable("owner/orders") {
                OwnerOrdersScreen(
                    onDetailClick = { /*detail */ },
                    currentRoute = "orders",
                    onNavigate = { shortRoute ->
                        when (shortRoute) {
                            "home" -> navController.navigate("owner/home")
                            "orders" -> navController.navigate("owner/orders")
                            "profile" -> navController.navigate("owner/profile")
                        }
                    }
                )
            }

            composable("owner/profile") {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Owner Profile")
                }
            }
        }
    }
}
