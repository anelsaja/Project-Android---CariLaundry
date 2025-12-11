package com.example.carilaundry.ui.feature.customer.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carilaundry.ui.theme.CariLaundryTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


data class CustomerOrder(
    val id: String,
    val laundryName: String,
    val service: String,
    val weightKg: Int,
    val totalText: String,
    val status: OrderStatus,
    val estimateText: String
)

enum class OrderStatus { PICKUP, PROGRESS, COMPLETED }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerOrdersScreen(
    modifier: Modifier = Modifier,
    orders: List<CustomerOrder> = remember {
        listOf(
            CustomerOrder(
                id = "ORD-001",
                laundryName = "Laundry Wertwer",
                service = "Cuci & Lipat",
                weightKg = 5,
                totalText = "Rp 40.000",
                status = OrderStatus.PICKUP,
                estimateText = "Kurir akan menjemput hari ini"
            ),
            CustomerOrder(
                id = "ORD-002",
                laundryName = "Laundry Hijau",
                service = "Cuci & Setrika",
                weightKg = 3,
                totalText = "Rp 24.000",
                status = OrderStatus.COMPLETED,
                estimateText = "Selesai"
            )
        )
    },
    onBack: () -> Unit = {},
    onOpenOrder: (String) -> Unit = {}
) {
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            TopAppBar(
                title = { Text(text = "Daftar Pesanan", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(orders) { order ->
                    OrderCard(order = order, onClick = { onOpenOrder(order.id) })
                }
            }
        }
    }
}

@Composable
private fun OrderCard(order: CustomerOrder, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Pesanan", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.weight(1f))
                Text(text = order.totalText, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Layanan", fontSize = 12.sp, color = Color.Gray)
                    Text(text = order.service, fontWeight = FontWeight.SemiBold)

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(text = "Berat", fontSize = 12.sp, color = Color.Gray)
                    Text(text = "${order.weightKg} kg", fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(horizontalAlignment = Alignment.End) {
                    val (statusText, statusColor) = when (order.status) {
                        OrderStatus.PICKUP -> "Proses Penjemputan" to MaterialTheme.colorScheme.primary
                        OrderStatus.PROGRESS -> "Proses Pengerjaan" to Color(0xFFB4C400)
                        OrderStatus.COMPLETED -> "Selesai" to Color(0xFF00C853)
                    }

                    Box(modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(statusColor)
                        .padding(horizontal = 12.dp, vertical = 6.dp)) {
                        Text(text = statusText, color = Color.White, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = order.estimateText, fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomerOrdersPreview() {
    CariLaundryTheme {
        CustomerOrdersScreen()
    }
}
