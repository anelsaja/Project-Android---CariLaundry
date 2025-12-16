package com.example.carilaundry.ui.feature.owner.pesanan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carilaundry.R
import com.example.carilaundry.ui.theme.*

// ================== DATA MODEL ==================
data class Order(
    val id: String,
    val customerName: String,
    val address: String,
    val service: String,
    val weightEstimate: String,
    val priceText: String
)

// ================== SCREEN ==================
@Composable
fun OwnerOrdersScreen(
    orders: List<Order> = remember { sampleOrders() },
    onDetailClick: (Order) -> Unit = {},
    onOpenNotifications: () -> Unit = {},
    onOpenProfile: () -> Unit = {}
) {
    // Variable search text (jika nanti ingin difungsikan)
    // var searchText by remember { mutableStateOf("") }

    Scaffold(
        containerColor = Background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            // 1. HEADER (Tanpa Favorite)
            HomeHeader(
                onNotifClick = onOpenNotifications,
                onProfileClick = onOpenProfile
            )

            // 2. TITLE SECTION
            Text(
                text = "Daftar Pesanan Masuk",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = OnBackground,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )

            // 3. LIST PESANAN
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(orders) { order ->
                    OrderItemCard(
                        order = order,
                        onClick = { onDetailClick(order) }
                    )
                }
            }
        }
    }
}

// ================== HEADER COMPONENT ==================
@Composable
fun HomeHeader(
    onNotifClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo",
            modifier = Modifier.size(40.dp)
        )

        Text(
            text = "CariLaundry Owner",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = OnBackground,
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        )

        // --- ICON FAVORITE DIHAPUS ---

        Icon(
            painter = painterResource(id = R.drawable.outline_notifications_24),
            contentDescription = "Notification",
            tint = OnBackground,
            modifier = Modifier
                .size(24.dp)
                .clickable { onNotifClick() }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            painter = painterResource(id = R.drawable.outline_account_circle_24),
            contentDescription = "Profile",
            tint = OnBackground,
            modifier = Modifier
                .size(24.dp)
                .clickable { onProfileClick() }
        )
    }
}


// ================== ORDER ITEM CARD ==================
@Composable
fun OrderItemCard(
    order: Order,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .background(Primary) // Menggunakan Primary color agar senada
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar circle
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = order.customerName.split(" ").firstOrNull()?.take(1) ?: "U",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = order.customerName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = OnPrimary
                )
                Text(
                    text = order.address,
                    fontSize = 12.sp,
                    color = OnPrimary.copy(alpha = 0.8f),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${order.service} • ${order.weightEstimate}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = OnPrimary
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Tombol Detail kecil
                Surface(
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.clickable { onClick() }
                ) {
                    Text(
                        text = "Detail",
                        color = Color.White,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = order.priceText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFB9F6CA) // A light green/white accent for price
                )
            }
        }
    }
}

// ================== DUMMY DATA ==================
fun sampleOrders() = List(5) {
    Order(
        id = it.toString(),
        customerName = "Mas Anel",
        address = "Jalan Senopati No. 3, Kampungin",
        service = "Cuci & Lipat",
        weightEstimate = "3kg",
        priceText = "Rp 24.000"
    )
}

@Preview(showBackground = true)
@Composable
fun OwnerOrdersPreview() {
    CariLaundryTheme {
        OwnerOrdersScreen()
    }
}