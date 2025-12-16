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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carilaundry.R
import com.example.carilaundry.domain.model.Laundry
import com.example.carilaundry.ui.AppViewModelProvider
import com.example.carilaundry.ui.theme.Primary

// Warna (Sebaiknya dipindah ke Color.kt nanti, tapi disini tidak apa-apa)
val LightBlueBg = Color(0xFFE0F7FA)
val PrimaryBlue = Color(0xFF3F7EC2)
val DarkBlueText = Color(0xFF1A237E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeskripsiScreen(
    modifier: Modifier = Modifier,
    // 1. Inject ViewModel (ID Laundry otomatis diambil dari Navigasi di dalam ViewModel ini)
    viewModel: DetailViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onBack: () -> Unit = {},
    onOrderNow: () -> Unit = {},
    onOpenMap: () -> Unit = {}
) {
    // 2. Ambil State UI
    val uiState = viewModel.detailUiState

    Scaffold(
        containerColor = LightBlueBg,
        bottomBar = {
            // Tampilkan bottom bar hanya jika data sukses dimuat
            if (uiState is DetailUiState.Success) {
                BottomSection(onOrderNow, onOpenMap)
            }
        }
    ) { innerPadding ->

        // 3. Cek Status Data (Loading / Success / Error)
        when (uiState) {
            is DetailUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PrimaryBlue)
                }
            }
            is DetailUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Gagal memuat detail laundry", color = Color.Red)
                }
            }
            is DetailUiState.Success -> {
                val laundry = uiState.laundry

                // 4. Tampilkan Data Asli
                DeskripsiContent(
                    laundry = laundry,
                    modifier = Modifier.padding(innerPadding),
                    onBack = onBack
                )
            }
        }
    }
}

// Konten dipisah agar kode lebih rapi
@Composable
fun DeskripsiContent(
    laundry: Laundry,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Column(
        modifier = modifier
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
                painter = painterResource(id = laundry.imageRes), // Gambar dari Model
                contentDescription = "Foto Laundry",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        /* ===== TITLE ===== */
        Text(
            text = laundry.name, // Nama dari Model
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryBlue,
            modifier = Modifier.padding(16.dp)
        )

        /* ===== INFO CARD ===== */
        // Catatan: Karena model Laundry kamu belum punya List Services,
        // kita pakai data static dulu untuk Services, tapi Address & Phone ambil dari Model
        InfoCardSection(
            address = laundry.address,
            phone = laundry.phone.ifEmpty { "+62 812-0000-0000" }, // Default jika kosong
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24), // Pastikan icon ada
            contentDescription = "Back",
            tint = Color(0xFF0D1B2A),
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
            color = Color(0xFF0D1B2A),
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
            fontSize = 18.sp, // Sedikit dikecilkan agar muat
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
                painter = painterResource(id = R.drawable.outline_location_on_24), // Pastikan icon ada
                contentDescription = "Map",
                tint = Color.White
            )
        }
    }
}

//// Untuk Preview: Kita buat fungsi dummy wrapper
//@Preview(showBackground = true)
//@Composable
//fun DeskripsiPreview() {
//    // Gunakan data dummy manual untuk preview
//    val dummyLaundry = Laundry("1", "Preview Laundry", "Jl. Test", "100m", R.drawable.icon)
//
//    // Panggil kontennya saja, bukan Screen utamanya (karena Screen utama butuh ViewModel)
//    Scaffold(containerColor = LightBlueBg) {
//        DeskripsiContent(
//            laundry = dummyLaundry,
//            modifier = Modifier.padding(it),
//            onBack = {}
//        )
//    }
//}