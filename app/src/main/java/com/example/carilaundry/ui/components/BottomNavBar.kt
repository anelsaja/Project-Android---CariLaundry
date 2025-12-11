package com.example.carilaundry.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object Orders : BottomNavItem("orders", "Pesanan", Icons.Default.List)
    object Profile : BottomNavItem("profile", "Profil", Icons.Default.AccountCircle)
}

@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(BottomNavItem.Home, BottomNavItem.Orders, BottomNavItem.Profile)

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}

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

    BottomNavBar(currentRoute = selectedRoute ?: "home", onNavigate = { shortRoute ->
        val target = if (basePrefix != null) "$basePrefix/$shortRoute" else shortRoute
        if (target != currentFullRoute) navController.navigate(target) {
            // preserve state behavior could be added here
            launchSingleTop = true
        }
    })
}
