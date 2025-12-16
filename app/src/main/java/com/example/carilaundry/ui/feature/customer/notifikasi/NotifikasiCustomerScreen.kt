package com.example.carilaundry.ui.feature.customer.notifikasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carilaundry.R
import com.example.carilaundry.ui.theme.CariLaundryTheme

// ================= DATA MODEL =================
data class NotificationItem(
    val id: String,
    val name: String,
    val address: String,
    val message: String,
    val time: String
)

// ================= SCREEN =================
@Composable
fun NotifikasiCustomerScreen(
    onBack: () -> Unit = {},
    onDetailClick: (String) -> Unit = {}
) {
    val notifList = List(6) {
        NotificationItem(
            id = it.toString(),
            name = "Mas Anel",
            address = "Jalan Senopati No. 3, Kampungin",
            message = "Melakukan Orderan Pada Laundry xxx",
            time = "2 jam yang lalu"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA))
    ) {

        // HEADER
        NotificationHeader(onBack)

        // TITLE
        Text(
            text = "Notifikasi",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D1B2A), // ⬅️ TAMBAH
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
        )


        // LIST
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(notifList) { item ->
                NotificationItemCard(
                    item = item,
                    onDetailClick = { onDetailClick(item.id) }
                )
            }
        }
    }
}

// ================= HEADER =================
@Composable
fun NotificationHeader(onBack: () -> Unit) {
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


// ================= ITEM CARD =================
@Composable
fun NotificationItemCard(
    item: NotificationItem,
    onDetailClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFF3F7EC2))
                .padding(16.dp)
        ) {

            // TOP ROW
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = item.address,
                    color = Color(0xFFD1E3F5),
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = item.time,
                    color = Color(0xFFD1E3F5),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // BOTTOM ROW
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = item.message,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(80.dp)
                        .height(30.dp)
                        .border(
                            1.dp,
                            Color.White,
                            RoundedCornerShape(20.dp)
                        )
                        .clickable { onDetailClick() }
                ) {
                    Text(
                        text = "Detail",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// ================= PREVIEW =================
@Preview(showBackground = true)
@Composable
fun NotifikasiCustomerPreview() {
    CariLaundryTheme {
        NotifikasiCustomerScreen()
    }
}
