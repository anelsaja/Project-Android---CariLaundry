package com.example.carilaundry.ui.feature.customer.detail_pesanan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carilaundry.R
import com.example.carilaundry.ui.theme.CariLaundryTheme

// ================= SCREEN =================
@Composable
fun DetailPesananCustomerScreen(
    laundryId: String? = null,
    onBack: () -> Unit = {},
    onPlaceOrder: (Int) -> Unit = {},
    onSubmitOrder: () -> Unit = {}
) {
    // State untuk Form
    // Opsi layanan sesuai deskripsi laundry
    val serviceOptions = listOf("Cuci & Lipat", "Cuci Kering", "Setrika Saja")
    var selectedService by remember { mutableStateOf(serviceOptions[0]) }

    var itemCategory by remember { mutableStateOf("") } // Input Text
    var weight by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var payment by remember { mutableStateOf("Tunai") }

    Scaffold(
        containerColor = Color(0xFFE0F7FA),
        bottomBar = {
            Button(
                onClick = onSubmitOrder,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F7EC2)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Pesan", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            OrderHeader(onBack)

            LaundryInfoCard()

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

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    InputLabel("Berat")
                    CustomTextField(weight, { weight = it }, "5 kg")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { /* scan */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 20.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F7EC2)),
                    shape = RoundedCornerShape(8.dp) // Samakan shape tombol scan
                ) {
                    Text("Scan")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            InputLabel("Catatan")
            CustomTextField(notes, { notes = it }, "Catatan tambahan (Opsional)", false, 80.dp)

            Spacer(modifier = Modifier.height(24.dp))
            SectionTitle("Pengambilan & Pengantaran")

            InputLabel("Tanggal")
            CustomTextField(date, { date = it }, "DD/MM/YYYY")

            Spacer(modifier = Modifier.height(12.dp))

            InputLabel("Alamat")
            CustomTextField(address, { address = it }, "Alamat lengkap pengiriman", false, 80.dp)

            Spacer(modifier = Modifier.height(24.dp))
            SectionTitle("Metode Pembayaran")

            PaymentOption("Bayar Tunai", payment == "Tunai") { payment = "Tunai" }
            PaymentOption("Transfer Bank", payment == "Transfer") { payment = "Transfer" }
            PaymentOption("E-Wallet", payment == "E-Wallet") { payment = "E-Wallet" }

            Spacer(modifier = Modifier.height(24.dp))
            SectionTitle("Harga")
            PricingSummaryCard()

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// ================= KOMPONEN =================

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
            "Order",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D1B2A),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
fun LaundryInfoCard() {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(90.dp)
                    .fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFF3F7EC2))
                    .padding(12.dp)
            ) {
                Text("Laundry Wertwer", color = Color.White, fontWeight = FontWeight.Bold)
                Text("Jalan Senopati No. 3", color = Color(0xFFE0E0E0), fontSize = 12.sp)
                Text("+62 812-2707-4781", color = Color(0xFFE0E0E0), fontSize = 12.sp)
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
    height: Dp = 50.dp
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 14.sp,
                color = Color(0xFF616161) // Warna placeholder lebih gelap (Abu Tua) agar kontras
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(Color.White, RoundedCornerShape(8.dp)), // Background putih
        singleLine = singleLine,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF3F7EC2), // Biru saat aktif
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
fun PricingSummaryCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            PriceRow("Subtotal (5 kg)", "Rp 40.000")
            PriceRow("Biaya Antar", "Gratis")
            Divider(color = Color.LightGray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 8.dp))
            PriceRow("Total", "Rp 40.000", true)
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
        fontWeight = FontWeight.Medium,
        color = Color(0xFF4A4A4A),
        modifier = Modifier.padding(bottom = 4.dp, top = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun OrderFormPreview() {
    CariLaundryTheme {
        DetailPesananCustomerScreen(laundryId = "1")
    }
}