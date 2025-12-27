package com.example.carilaundry.ui.feature.customer.deskripsi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carilaundry.R
import com.example.carilaundry.domain.model.Laundry
import com.example.carilaundry.ui.AppViewModelProvider

// Warna
val LightBlueBg = Color(0xFFE0F7FA)
val PrimaryBlue = Color(0xFF3F7EC2)
val DarkBlueText = Color(0xFF1A237E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeskripsiScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onBack: () -> Unit = {},
    onOrderNow: () -> Unit = {}, // Nanti ini kirim ID laundry
    onOpenMap: () -> Unit = {}
) {
    // 1. Ambil State dengan collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = LightBlueBg,
        bottomBar = {
            // Tampilkan bottom bar hanya jika laundry ada
            if (uiState.laundry != null) {
                BottomSection(onOrderNow, onOpenMap)
            }
        }
    ) { innerPadding ->

        // 2. Logika Tampilan Berdasarkan State Data Class
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

            // A. Jika Loading
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = PrimaryBlue)
                }
            }

            // B. Jika Sukses (Data Ada)
            val data = uiState.laundry
            if (data != null) {
                DeskripsiContent(
                    laundry = data,
                    modifier = Modifier.fillMaxSize(),
                    onBack = onBack
                )
            }

            // C. Jika Error
            if (uiState.errorMessage != null && uiState.laundry == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = uiState.errorMessage ?: "Error", color = Color.Red)
                }
            }
        }
    }
}

// ================================================================
// BAGIAN KE BAWAH INI TETAP SAMA DENGAN KODEMU SEBELUMNYA (SUDAH BAGUS)
// ================================================================

@Composable
fun DeskripsiContent(
    laundry: Laundry,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
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
                painter = painterResource(id = laundry.imageRes),
                contentDescription = "Foto Laundry",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        /* ===== TITLE ===== */
        Text(
            text = laundry.name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryBlue,
            modifier = Modifier.padding(16.dp)
        )

        /* ===== INFO CARD ===== */
        InfoCardSection(
            address = laundry.address,
            // Pastikan field phone sudah ditambahkan di Data Class Laundry
            phone = if (laundry.phone.isNotEmpty()) laundry.phone else "+62 812-0000-0000",
            services = listOf(
                "Cuci & Lipat" to "Mulai Rp 6.000",
                "Cuci Kering" to "Mulai Rp 10.000",
                "Setrika Saja" to "Mulai Rp 4.000"
            )
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun HeaderSection(onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = "Back",
            tint = Color(0xFF0D1B2A),
            modifier = Modifier.size(24.dp).clickable { onBack() }
        )
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo",
            modifier = Modifier.padding(start = 16.dp).size(40.dp)
        )
        Text(
            text = "Detail Laundry",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D1B2A),
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun InfoCardSection(address: String, phone: String, services: List<Pair<String, String>>) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.padding(bottom = 20.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = address, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = PrimaryBlue, modifier = Modifier.weight(1f))
                Text(text = phone, fontSize = 11.sp, color = PrimaryBlue)
            }
            services.forEachIndexed { index, item ->
                PriceRow(item.first, item.second)
                if (index != services.lastIndex) Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun PriceRow(service: String, price: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = service, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkBlueText)
        Text(text = price, color = PrimaryBlue, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun BottomSection(onOrder: () -> Unit, onOpenMap: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().background(LightBlueBg).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Button(
            onClick = onOrder,
            modifier = Modifier.weight(1f).height(50.dp),
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
            Icon(painter = painterResource(id = R.drawable.outline_location_on_24), contentDescription = "Map", tint = Color.White)
        }
    }
}