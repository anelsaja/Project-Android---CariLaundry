package com.example.carilaundry.ui.feature.customer.detail_pesanan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
// ... (lanjutkan dengan import lainnya yang sudah ada di bawah)

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.carilaundry.R
import com.example.carilaundry.domain.model.Laundry
import com.example.carilaundry.ui.AppViewModelProvider
import java.text.NumberFormat
import java.util.Locale

// ================= SCREEN =================
@Composable
fun DetailPesananCustomerScreen(
    laundryId: String? = null,
    viewModel: OrderViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onBack: () -> Unit = {},
    onSubmitOrder: () -> Unit = {} // Callback setelah sukses submit
) {
    // Ambil State dari ViewModel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // State Lokal untuk Form (Input User)
    val serviceOptions = listOf("Cuci & Lipat", "Cuci Kering", "Setrika Saja")
    var selectedService by remember { mutableStateOf(serviceOptions[0]) }
    var itemCategory by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var payment by remember { mutableStateOf("Tunai") }

    Scaffold(
        containerColor = Color(0xFFE0F7FA),
        bottomBar = {
            // Tombol Pesan hanya aktif jika data laundry sudah berhasil dimuat (State Success)
            if (uiState is OrderUiState.Success) {
                Button(
                    onClick = {
                        // Di sini logika submit order ke ViewModel seharusnya dipanggil
                        // viewModel.submitOrder(...)

                        // Untuk sekarang panggil callback navigasi
                        onSubmitOrder()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F7EC2)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Pesan Sekarang", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { padding ->

        // Handle Status UI (Loading / Success / Error)
        when (val state = uiState) {
            is OrderUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF3F7EC2))
                }
            }
            is OrderUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Gagal memuat data: ${state.message}",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            is OrderUiState.Success -> {
                val laundry = state.laundry

                // KONTEN UTAMA FORM
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    OrderHeader(onBack)

                    // Kirim data laundry asli ke InfoCard
                    LaundryInfoCard(laundry)

                    Spacer(modifier = Modifier.height(16.dp))
                    SectionTitle("Layanan")

                    // 1. DROPDOWN JENIS LAYANAN
                    InputLabel("Jenis Layanan")
                    CustomDropdownInput(
                        options = serviceOptions,
                        selectedOption = selectedService,
                        onOptionSelected = { selectedService = it }
                    )

                    // 2. TEXT INPUT KATEGORI ITEM
                    InputLabel("Kategori Item")
                    CustomTextField(
                        value = itemCategory,
                        onValueChange = { itemCategory = it },
                        placeholder = "Contoh: Baju, Celana, Selimut"
                    )

                    // 3. INPUT BERAT (Terhubung ke ViewModel)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            InputLabel("Berat (Kg)")
                            CustomTextField(
                                value = state.weightInput,
                                onValueChange = { viewModel.onWeightChanged(it) }, // Hitung harga realtime
                                placeholder = "0",
                                keyboardType = KeyboardType.Number // Keyboard Angka
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { /* TODO: Implementasi Fitur Scan QR */ },
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 20.dp)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F7EC2)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Scan")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    InputLabel("Catatan")
                    CustomTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        placeholder = "Catatan tambahan (Opsional)",
                        singleLine = false,
                        height = 80.dp
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    SectionTitle("Pengambilan & Pengantaran")

                    InputLabel("Tanggal")
                    CustomTextField(
                        value = date,
                        onValueChange = { date = it },
                        placeholder = "DD/MM/YYYY"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InputLabel("Alamat")
                    CustomTextField(
                        value = address,
                        onValueChange = { address = it },
                        placeholder = "Alamat lengkap pengiriman",
                        singleLine = false,
                        height = 80.dp
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    SectionTitle("Metode Pembayaran")

                    PaymentOption("Bayar Tunai", payment == "Tunai") { payment = "Tunai" }
                    PaymentOption("Transfer Bank", payment == "Transfer") { payment = "Transfer" }
                    PaymentOption("E-Wallet", payment == "E-Wallet") { payment = "E-Wallet" }

                    Spacer(modifier = Modifier.height(24.dp))
                    SectionTitle("Estimasi Harga")

                    // Tampilkan Harga Hasil Hitungan
                    PricingSummaryCard(
                        weight = state.weightInput,
                        totalPrice = state.estimatedPrice
                    )

                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

// ================= KOMPONEN UI =================

@Composable
fun OrderHeader(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = "Back",
            tint = Color(0xFF0D1B2A),
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterStart)
                .clickable { onBack() }
        )
        Text(
            "Form Pemesanan",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D1B2A),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun LaundryInfoCard(laundry: Laundry) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row {
            // Gambar Laundry
            Image(
                painter = painterResource(id = laundry.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(90.dp)
                    .fillMaxHeight()
            )
            // Detail Laundry
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFF3F7EC2))
                    .padding(12.dp)
            ) {
                Text(laundry.name, color = Color.White, fontWeight = FontWeight.Bold)
                Text(laundry.address, color = Color(0xFFE0E0E0), fontSize = 12.sp, maxLines = 1)

                // Jika model Laundry punya field phone, tampilkan. Jika tidak, pakai placeholder.
                // Text(laundry.phone, color = Color(0xFFE0E0E0), fontSize = 12.sp)
                Text("Hubungi Penjual", color = Color(0xFFE0E0E0), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean = true,
    height: Dp = 50.dp,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 14.sp,
                color = Color(0xFF616161)
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(Color.White, RoundedCornerShape(8.dp)),
        singleLine = singleLine,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF3F7EC2),
            unfocusedBorderColor = Color.LightGray,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = Color(0xFF3F7EC2)
        )
    )
}

@Composable
fun CustomDropdownInput(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .clickable { expanded = true }
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = selectedOption,
                fontSize = 14.sp,
                color = Color.Black
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown",
                tint = Color.Black
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option, color = Color.Black) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun PaymentOption(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = if (selected) Color(0xFF3F7EC2) else Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF3F7EC2),
                unselectedColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            label,
            color = Color(0xFF0D1B2A),
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun PricingSummaryCard(
    weight: String,
    totalPrice: Double
) {
    // Format Rupiah
    val formatRp = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    val priceString = formatRp.format(totalPrice)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            val berat = if(weight.isEmpty()) "0" else weight
            PriceRow("Subtotal ($berat kg)", priceString)
            PriceRow("Biaya Antar", "Gratis") // Bisa dibuat dinamis nanti
            Divider(color = Color.LightGray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))
            PriceRow("Total", priceString, bold = true)
        }
    }
}

@Composable
fun PriceRow(label: String, value: String, bold: Boolean = false) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Text(label, fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal, color = Color.Black)
        Text(value, fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal, color = Color.Black)
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF0D1B2A)
    )
}

@Composable
fun InputLabel(text: String) {
    Text(
        text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF0D1B2A),
        modifier = Modifier.padding(bottom = 4.dp)
    )
}
