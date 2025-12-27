package com.example.carilaundry.ui.feature.customer.order_success

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carilaundry.R
import com.example.carilaundry.ui.AppViewModelProvider
import com.example.carilaundry.ui.feature.customer.register.CustomerRegisterScreen
import com.example.carilaundry.ui.theme.CariLaundryTheme

@Composable
fun OrderSuccessScreen(
    // Inject ViewModel
    viewModel: OrderSuccessViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onOk: () -> Unit = {}
) {
    // Ambil State
    val uiState by viewModel.uiState.collectAsState()

    val BackgroundColor = Color(0xFFE0F7FA)
    val PrimaryBlue = Color(0xFF3F7EC2)
    val DarkBlueText = Color(0xFF1A237E)
    val LightBlueText = Color(0xFF5C6BC0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        /* ===== TITLE ===== */
        Text(
            text = "Order Berhasil",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        /* ===== IMAGE ===== */
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "Foto Laundry",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        /* ===== LAUNDRY NAME (DARI STATE) ===== */
        Text(
            text = uiState.laundryName, // Menggunakan data dinamis
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryBlue,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        /* ===== CONFIRMATION CARD ===== */
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                /* ===== ADDRESS (DARI STATE) ===== */
                Text(
                    text = uiState.laundryAddress, // Menggunakan data dinamis
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp)
                )

                Text(
                    text = "Order Anda diterima",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlueText,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Terima kasih telah menggunakan jasa kami",
                    fontSize = 12.sp,
                    color = LightBlueText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 40.dp)
                )

                Button(
                    onClick = onOk,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                ) {
                    Text(
                        text = "OKE",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomerRegisterPreview() {
    CariLaundryTheme {
        OrderSuccessScreen()
    }
}