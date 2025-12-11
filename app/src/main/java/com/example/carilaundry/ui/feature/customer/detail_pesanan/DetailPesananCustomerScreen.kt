package com.example.carilaundry.ui.feature.customer.detail_pesanan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPesananCustomerScreen(
    modifier: Modifier = Modifier,
    laundryId: String? = null,
    laundryName: String = "Laundry Wertwer",
    address: String = "Jalan Senopati No. 3, Kampungin",
    phone: String = "+62 812-2707-4781",
    onBack: () -> Unit = {},
    onPlaceOrder: (total: Int) -> Unit = {}
) {
    var layanan by remember { mutableStateOf("Cuci dan Lipat") }
    var kategori by remember { mutableStateOf("Baju dan Celana") }
    var lamaCuci by remember { mutableStateOf("3 Hari") }
    var berat by remember { mutableStateOf("5") } // numeric only
    var catatan by remember { mutableStateOf("") }

    var pengambilanMethod by remember { mutableStateOf("Antar Sendiri") }
    var waktu by remember { mutableStateOf("") }
    var alamatPengantaran by remember { mutableStateOf("") }

    var pembayaranMethod by remember { mutableStateOf("Bayar Tunai") }

    val hargaPerKg = 8000
    val subtotal = (berat.toIntOrNull() ?: 0) * hargaPerKg
    val biayaAntar = if (pengambilanMethod == "Antar") 10000 else 0
    val total = subtotal + biayaAntar

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                TopAppBar(
                    title = { Text(text = "Order", fontWeight = FontWeight.SemiBold) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }

            item {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier
                        .padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

                        Box(modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White), contentAlignment = Alignment.Center) {
                            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "logo", tint = MaterialTheme.colorScheme.primary)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = laundryName + (laundryId?.let { " (id:$it)" } ?: ""), fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = address, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.9f))
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = phone, style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.95f))
                        }

                        OutlinedButton(onClick = { /* open */ }, modifier = Modifier
                            .height(36.dp), colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)) {
                            Text(text = "Detail", color = Color.White)
                        }
                    }
                }
            }

            item {
                Text(text = "Layanan", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }

            fun dropdownField(value: String, label: String, onClick: () -> Unit) = @Composable {
                OutlinedTextField(
                    value = value,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick() },
                    readOnly = true,
                    label = { Text(label) },
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                )
            }

            item {
                dropdownField(layanan, "Jenis Layanan") { /* show options */ }()
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                dropdownField(kategori, "Kategori Item") { /* show options */ }()
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                dropdownField(lamaCuci, "Lama Cuci") { /* show options */ }()
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = berat,
                        onValueChange = { berat = it.filter { ch -> ch.isDigit() } },
                        label = { Text("Berat (kg)") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = { /* scann */ }, modifier = Modifier
                        .height(48.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary), shape = RoundedCornerShape(8.dp)) {
//                        icon() ===> nnti ksi icon scan
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Scan Cucian", color = Color.White)

                        //keknya hsrus sesuaikan metode hitung harga
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                OutlinedTextField(value = catatan, onValueChange = { catatan = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Catatan") })
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                Text(text = "Pengambilan dan Pengantaran", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = pengambilanMethod == "Antar", onClick = { pengambilanMethod = "Antar" })
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Antar", modifier = Modifier.clickable { pengambilanMethod = "Antar" })

                        Spacer(modifier = Modifier.width(12.dp))

                        RadioButton(selected = pengambilanMethod == "Jemput", onClick = { pengambilanMethod = "Jemput" })
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Jemput", modifier = Modifier.clickable { pengambilanMethod = "Jemput" })

                        Spacer(modifier = Modifier.width(12.dp))

                        RadioButton(selected = pengambilanMethod == "Antar Sendiri", onClick = { pengambilanMethod = "Antar Sendiri" })
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Antar Sendiri", modifier = Modifier.clickable { pengambilanMethod = "Antar Sendiri" })
                    }

                    OutlinedTextField(value = waktu, onValueChange = { waktu = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Waktu") })
                    OutlinedTextField(value = alamatPengantaran, onValueChange = { alamatPengantaran = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Alamat") })
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                Text(text = "Metode Pembayaran", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    val methods = listOf("Bayar Tunai", "Transfer Bank", "E-Wallet")
                    methods.forEach { m ->
                        val selected = pembayaranMethod == m
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { pembayaranMethod = m },
                            shape = RoundedCornerShape(8.dp),
                            border = if (selected) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null,
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(if (selected) MaterialTheme.colorScheme.primary else Color.LightGray), contentAlignment = Alignment.Center) {
                                    if (selected) Icon(imageVector = Icons.Default.Check, contentDescription = "selected", tint = Color.White, modifier = Modifier.size(12.dp))
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(text = m, fontWeight = FontWeight.SemiBold)
                                    Text(text = when (m) {
                                        "Bayar Tunai" -> "Bayar saat kurir tiba"
                                        "Transfer Bank" -> "Transfer via bank"
                                        else -> "Pembayaran digital"
                                    }, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                Text(text = "Harga", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }

            item {
                Card(shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Subtotal (${berat} kg)")
                            Text(text = String.format(Locale.getDefault(), "Rp %,d", subtotal))
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Biaya Antar")
                            Text(text = if (biayaAntar == 0) "Gratis" else String.format(Locale.getDefault(), "Rp %,d", biayaAntar))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "Total", fontWeight = FontWeight.Bold)
                            Text(text = String.format(Locale.getDefault(), "Rp %,d", total), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }

            item {
                Button(onClick = { onPlaceOrder(total) }, modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary), shape = RoundedCornerShape(12.dp)) {
                    Text(text = "Pesan", color = Color.White, fontSize = 16.sp)
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPesananCustomerPreview() {
    CariLaundryTheme {
        DetailPesananCustomerScreen(laundryId = "1")
    }
}
