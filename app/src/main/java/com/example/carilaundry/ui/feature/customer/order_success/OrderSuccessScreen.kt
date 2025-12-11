package com.example.carilaundry.ui.feature.customer.order_success

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderSuccessScreen(
    modifier: Modifier = Modifier,
    laundryName: String = "Laundry Wertwer",
    address: String = "Jalan Senopati No. 3, Kampungin",
    onBack: () -> Unit = {},
    onOk: () -> Unit = {}
) {
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            TopAppBar(
                title = { Text(text = "Order Berhasil", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Image Banner", color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = laundryName, style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = address, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)

            Spacer(modifier = Modifier.height(12.dp))

            Card(shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Order Anda diterima", fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.CenterHorizontally))
                    Text(text = "Terima kasih telah menggunakan jasa kami", color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = onOk, modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                        Text(text = "OKE", color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderSuccessPreview() {
    CariLaundryTheme {
        OrderSuccessScreen()
    }
}
