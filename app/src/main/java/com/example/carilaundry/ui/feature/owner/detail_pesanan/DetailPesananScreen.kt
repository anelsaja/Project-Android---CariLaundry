package com.example.carilaundry.ui.feature.owner.detail_pesanan

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.carilaundry.R
import com.example.carilaundry.domain.model.OwnerOrderDetail
import com.example.carilaundry.ui.AppViewModelProvider
import com.example.carilaundry.ui.theme.CariLaundryTheme

@Composable
fun DetailPesananScreen(
    viewModel: DetailOwnerViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onBack: () -> Unit = {},
    onProcessPickup: (String) -> Unit = {},
    onProcessProgress: (String) -> Unit = {},
    onComplete: (String) -> Unit = {}
) {
    // Gunakan collectAsStateWithLifecycle untuk performa yang lebih baik
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = Color(0xFFE0F7FA),
        bottomBar = {
            val detail = uiState.detail
            if (detail != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onProcessPickup(detail.id) },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F7EC2)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Proses Penjemputan", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { onProcessProgress(detail.id) },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3399FF)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Proses Pengerjaan", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { onComplete(detail.id) },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Selesai", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { padding ->

        Box(modifier = Modifier.padding(padding).fillMaxSize()) {

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF3F7EC2)
                )
            }

            val detail = uiState.detail
            if (detail != null) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    OwnerDetailHeader(onBack)
                    CustomerInfoCard(detail)

                    Spacer(modifier = Modifier.height(24.dp))

                    SectionTitle("Detail Layanan")
                    InfoLabel("Jenis Layanan")
                    InfoValueBox(detail.service)
                    InfoLabel("Kategori Item")
                    InfoValueBox(detail.category)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            InfoLabel("Berat")
                            InfoValueBox("${detail.weightKg} kg")
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            InfoLabel("Durasi")
                            InfoValueBox(detail.duration)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    InfoLabel("Catatan Pelanggan")
                    InfoValueBox(if (detail.notes.isNotEmpty()) detail.notes else "-")

                    Spacer(modifier = Modifier.height(24.dp))

                    SectionTitle("Info Pengambilan")
                    InfoLabel("Metode")
                    InfoValueBox(detail.pickupMethod)
                    InfoLabel("Waktu")
                    InfoValueBox(detail.time)
                    InfoLabel("Alamat")
                    InfoValueBox(detail.address, isMultiLine = true)

                    Spacer(modifier = Modifier.height(24.dp))

                    SectionTitle("Metode Pembayaran")
                    InfoValueBox(detail.paymentMethod)

                    Spacer(modifier = Modifier.height(24.dp))

                    SectionTitle("Rincian Harga")
                    PricingSummaryCard(detail)

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            if (uiState.errorMessage != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${uiState.errorMessage}", color = Color.Red)
                }
            }
        }
    }
}

// ================= KOMPONEN HELPER =================

@Composable
fun OwnerDetailHeader(onBack: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = "Back",
            tint = Color(0xFF0D1B2A),
            modifier = Modifier.size(24.dp).align(Alignment.CenterStart).clickable { onBack() }
        )
        Text(
            "Detail Pesanan",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D1B2A),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun CustomerInfoCard(detail: OwnerOrderDetail) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth().height(100.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row {
            Box(
                modifier = Modifier.width(90.dp).fillMaxHeight().background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(text = detail.customerName.take(1).uppercase(), fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3F7EC2))
            }
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight().background(Color(0xFF3F7EC2)).padding(12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = detail.customerName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "Pelanggan", color = Color(0xFFE0E0E0), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun InfoValueBox(text: String, isMultiLine: Boolean = false) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isMultiLine) 80.dp else 50.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFB0BEC5), RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = if (isMultiLine) Alignment.TopStart else Alignment.CenterStart
    ) {
        Text(text = text, fontSize = 14.sp, color = Color.Black)
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun PricingSummaryCard(detail: OwnerOrderDetail) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            PriceRow("Subtotal (${detail.weightKg} kg)", detail.subtotalText)
            Spacer(modifier = Modifier.height(8.dp))
            PriceRow("Biaya Antar", detail.deliveryFeeText)
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.height(8.dp))
            PriceRow("Total", detail.totalText, bold = true)
        }
    }
}

@Composable
fun PriceRow(label: String, value: String, bold: Boolean = false) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Text(label, fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal, fontSize = 14.sp)
        Text(value, fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal, fontSize = 14.sp)
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0D1B2A), modifier = Modifier.padding(bottom = 8.dp))
}

@Composable
fun InfoLabel(text: String) {
    Text(text, fontSize = 12.sp, color = Color(0xFF546E7A), modifier = Modifier.padding(bottom = 4.dp))
}

@Preview(showBackground = true)
@Composable
fun DetailPesananOwnerPreview() {
    CariLaundryTheme {
        DetailPesananScreen()
    }
}