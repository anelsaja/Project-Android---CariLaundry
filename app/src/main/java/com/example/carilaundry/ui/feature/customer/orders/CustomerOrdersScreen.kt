package com.example.carilaundry.ui.feature.customer.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carilaundry.R
import com.example.carilaundry.ui.theme.CariLaundryTheme
import androidx.compose.ui.res.painterResource
import com.example.carilaundry.ui.theme.OnBackground
import com.example.carilaundry.ui.theme.OnPrimary

// ================= DATA =================
data class CustomerOrder(
    val id: String,
    val service: String,
    val weight: String,
    val total: String,
    val status: String,
    val estimation: String
)

// ================= SCREEN =================
@Composable
fun CustomerOrdersScreen(
    orders: List<CustomerOrder> = remember {
        listOf(
            CustomerOrder("1", "Cuci & Lipat", "5 kg", "Rp 40.000", "Proses Pengerjaan", "Besok, 14:00"),
            CustomerOrder("2", "Cuci Kering", "2 pcs", "Rp 75.000", "Menunggu Kurir", "Hari ini, 17:00"),
            CustomerOrder("3", "Setrika Saja", "3 kg", "Rp 15.000", "Selesai", "Kemarin")
        )
    },
    onBack: () -> Unit = {},
    onOpenOrder: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA))
    ) {

        // 🔷 HEADER (SAMA SEPERTI PROFILE)
        OrdersHeader(onBack)

        // 🔷 TITLE
        Text(
            text = "Daftar Pesanan",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(start = 20.dp, bottom = 16.dp)
        )

        // 🔷 LIST ORDER
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(orders) { order ->
                OrderCard(order) {
                    onOpenOrder(order.id)
                }
            }
        }
    }
}

// ================= HEADER =================
@Composable
fun OrdersHeader(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = "Back",
            tint = Color(0xFF0D1B2A), // ⬅️ TAMBAH
            modifier = Modifier
                .size(24.dp)
                .clickable { onBack() }
        )

        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo",
            modifier = Modifier
                .padding(start = 16.dp)
                .size(40.dp)
        )

        Text(
            text = "CariLaundry",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D1B2A), // ⬅️ TAMBAH
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

// ================= ORDER CARD =================
@Composable
fun OrderCard(order: CustomerOrder, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Pesanan",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4285F4)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OrderRow("Layanan", order.service)
            OrderRow("Berat", order.weight)
            OrderRow("Total", order.total)

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFA0A237), RoundedCornerShape(6.dp))
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = order.status,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Estimasi: ${order.estimation}",
                fontSize = 12.sp,
                color = Color(0xFF4285F4)
            )
        }
    }
}

// ================= HELPER =================
@Composable
fun OrderRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = OnBackground)
        Text(text = value, fontWeight = FontWeight.Bold, color = OnBackground)
    }
}

// ================= PREVIEW =================
@Preview(showBackground = true)
@Composable
fun OrdersPreview() {
    CariLaundryTheme {
        CustomerOrdersScreen()
    }
}
