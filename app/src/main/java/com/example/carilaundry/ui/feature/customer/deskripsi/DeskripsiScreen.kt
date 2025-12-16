package com.example.carilaundry.ui.feature.customer.deskripsi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carilaundry.R

// Warna
val LightBlueBg = Color(0xFFE0F7FA)
val PrimaryBlue = Color(0xFF3F7EC2)
val DarkBlueText = Color(0xFF1A237E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeskripsiScreen( // NAMA FUNGSI DISESUAIKAN DENGAN NAVGRAPH
    laundryId: String? = null, // DITAMBAHKAN AGAR SESUAI NAVGRAPH
    laundryName: String = "Laundry Wertwer",
    address: String = "Jalan Senopati No. 3, Kampungin",
    phone: String = "+62 812-2707-4781",
    services: List<Pair<String, String>> = listOf(
        "Cuci & Lipat" to "Rp 8.000/kg",
        "Cuci Kering" to "Rp 10.000/kg",
        "Setrika Saja" to "Rp 6.000/kg"
    ),
    onBack: () -> Unit = {},
    onOrderNow: () -> Unit = {}, // UBAH NAMA PARAMETER DARI onOrder KE onOrderNow
    onOpenMap: () -> Unit = {}
) {
    Scaffold(
        containerColor = LightBlueBg,
        bottomBar = {
            BottomSection(onOrderNow, onOpenMap)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {

            HeaderSection(onBack)

            /* ===== IMAGE ===== */
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = "Foto Laundry",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            /* ===== TITLE ===== */
            Text(
                text = laundryName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue,
                modifier = Modifier.padding(16.dp)
            )

            /* ===== INFO CARD ===== */
            InfoCardSection(address, phone, services)

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun HeaderSection(onBack: () -> Unit) {
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
            text = "Detail Laundry",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D1B2A), // ⬅️ TAMBAH
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun InfoCardSection(
    address: String,
    phone: String,
    services: List<Pair<String, String>>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = address,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = PrimaryBlue,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = phone,
                    fontSize = 11.sp,
                    color = PrimaryBlue
                )
            }
            services.forEachIndexed { index, item ->
                PriceRow(item.first, item.second)
                if (index != services.lastIndex) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun PriceRow(service: String, price: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = service,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlueText
        )
        Text(
            text = price,
            color = PrimaryBlue,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun BottomSection(
    onOrder: () -> Unit,
    onOpenMap: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightBlueBg)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onOrder,
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
        ) {
            Text("Pesan Sekarang", fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(12.dp))
        Button(
            onClick = onOpenMap,
            modifier = Modifier.size(50.dp),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_location_on_24), // Pastikan icon ini ada atau ganti icon lain
                contentDescription = "Map",
                tint = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeskripsiPreview() {
    DeskripsiScreen()
}