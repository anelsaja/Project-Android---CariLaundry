package com.example.carilaundry.ui.feature.owner.detail_pesanan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carilaundry.ui.theme.CariLaundryTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Immutable
data class OwnerOrderDetail(
    val id: String,
    val laundryName: String,
    val service: String,
    val category: String,
    val duration: String,
    val weightKg: Int,
    val notes: String,
    val pickupMethod: String,
    val time: String,
    val address: String,
    val paymentMethod: String,
    val subtotalText: String,
    val deliveryFeeText: String,
    val totalText: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPesananScreen(
    modifier: Modifier = Modifier,
    detail: OwnerOrderDetail,
    onBack: () -> Unit = {},
    onProcessPickup: (String) -> Unit = {},
    onProcessProgress: (String) -> Unit = {},
    onComplete: (String) -> Unit = {}
) {
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            TopAppBar(
                title = { Text(text = "Detail Pesanan", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )

            // Header card with laundry info
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "L", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = detail.laundryName, fontWeight = FontWeight.SemiBold, color = Color.White)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = detail.address, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.9f))
                    }
                }
            }

            @Composable
            fun infoRow(label: String, value: String) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = label, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .padding(12.dp)
                    ) {
                        Text(text = value, color = MaterialTheme.colorScheme.onBackground)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            infoRow("Jenis Layanan", detail.service)
            infoRow("Kategori Item", detail.category)
            infoRow("Lama Cuci", detail.duration)
            infoRow("Berat", "${detail.weightKg} kg")
            infoRow("Catatan", if (detail.notes.isNotBlank()) detail.notes else "Tidak ada")

            infoRow("Metode", detail.pickupMethod)
            infoRow("Waktu", detail.time)
            infoRow("Alamat", detail.address)
            infoRow("Metode Pembayaran", detail.paymentMethod)

            // Harga summary
            Card(shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Subtotal (${detail.weightKg} kg)")
                        Text(text = detail.subtotalText)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Biaya Antar")
                        Text(text = detail.deliveryFeeText)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Total", fontWeight = FontWeight.Bold)
                        Text(text = detail.totalText, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { onProcessPickup(detail.id) },
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Proses Penjemputan", color = Color.White)
                }
                Button(onClick = { /*jemput*/ }, modifier = Modifier.fillMaxWidth().height(44.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3399FF))) {
                Button(onClick = { onProcessProgress(detail.id) }, modifier = Modifier.fillMaxWidth().height(44.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3399FF))) {
                    Text(text = "Proses Pengerjaan", color = Color.White)
                }
                Button(onClick = { /*jemput*/ }, modifier = Modifier.fillMaxWidth().height(44.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853))) {
                Button(onClick = { onComplete(detail.id) }, modifier = Modifier.fillMaxWidth().height(44.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853))) {
                    Text(text = "Selesai", color = Color.White)
                }
            }
        }
    }
}

// Sample for preview
fun sampleOwnerOrder() = OwnerOrderDetail(
    id = "ORD-001",
    laundryName = "Laundry Wertwer",
    service = "Cuci dan Lipat",
    category = "Baju dan Celana",
    duration = "3 Hari",
    weightKg = 5,
    notes = "Tidak ada",
    pickupMethod = "Jemput",
    time = "05/11/2025",
    address = "Jl. Soekarno Hatta No. 15, Kampungin",
    paymentMethod = "Bayar Tunai",
    subtotalText = "Rp 40.000",
    deliveryFeeText = "Gratis",
    totalText = "Rp 40.000"
)


@Composable
fun DetailPesananOwnerPreview() {
    CariLaundryTheme {
        DetailPesananScreen(detail = sampleOwnerOrder())
    }
}}}
