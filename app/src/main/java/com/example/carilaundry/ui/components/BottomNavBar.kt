package com.example.carilaundry.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.carilaundry.ui.theme.*

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object Orders : BottomNavItem("orders", "Pesanan", Icons.Default.List)
    object Profile : BottomNavItem("profile", "Profil", Icons.Default.AccountCircle)
}

/* =========================================
   BASE BOTTOM NAV
   ========================================= */
@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Orders,
        BottomNavItem.Profile
    )

    NavigationBar(
        containerColor = Surface,
        tonalElevation = 4.dp
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(text = item.label)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = OnSurface,
                    selectedTextColor = OnSurface,
                    unselectedIconColor = OnSurface.copy(alpha = 0.6f),
                    unselectedTextColor = OnSurface.copy(alpha = 0.6f),
                    indicatorColor = PrimaryContainer
                )
            )
        }
    }
}

/* =========================================
   NAV CONTROLLER WRAPPER
   ========================================= */
@Composable
fun BottomNavBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentFullRoute = backStackEntry?.destination?.route

    val basePrefix = when {
        currentFullRoute?.startsWith("customer/") == true -> "customer"
        currentFullRoute?.startsWith("owner/") == true -> "owner"
        else -> null
    }

    val selectedRoute = currentFullRoute?.substringAfter("/")

    BottomNavBar(
        currentRoute = selectedRoute ?: "home",
        onNavigate = { shortRoute ->
            val target =
                if (basePrefix != null) "$basePrefix/$shortRoute"
                else shortRoute

            if (target != currentFullRoute) {
                navController.navigate(target) {
                    launchSingleTop = true
                }
            }
        }
    )
}
