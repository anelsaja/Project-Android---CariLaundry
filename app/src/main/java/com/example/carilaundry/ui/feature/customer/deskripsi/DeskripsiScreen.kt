package com.example.carilaundry.ui.feature.customer.deskripsi

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
import androidx.compose.ui.unit.sp
import com.example.carilaundry.ui.theme.CariLaundryTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeskripsiScreen(
    modifier: Modifier = Modifier,
    laundryId: String? = null,
    laundryName: String = "Laundry Wertwer",
    address: String = "Jalan Senopati No. 3, Kampungin",
    phone: String = "+62 812-2707-4781",
    services: List<Pair<String, String>> = listOf(
        "Cuci & Lipat" to "Rp 8.000/kg",
        "Cuci & Lipat" to "Rp 8.000/kg",
        "Cuci & Lipat" to "Rp 8.000/kg"
    ),
    onBack: () -> Unit = {},
    onOrderNow: () -> Unit = {},
    onOpenMap: () -> Unit = {}
) {
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            TopAppBar(
                title = { Text(text = "Laundry", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // placeholder for notification / other actions
                    IconButton(onClick = {}) {
                        // empty so it lines up similarly to design
                        Box(modifier = Modifier.size(24.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Image Banner", color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = laundryName,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = address, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = Icons.Filled.Phone, contentDescription = "phone", tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = phone, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp)
                ) {
                    services.forEachIndexed { index, pair ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = pair.first, style = MaterialTheme.typography.bodyLarge, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                                Text(text = "Detail: -", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            }

                            Text(text = pair.second, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }

                        if (index != services.lastIndex) {
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = onOrderNow,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Pesan Sekarang", color = Color.White)
                }

                Spacer(modifier = Modifier.width(12.dp))

                IconButton(onClick = onOpenMap, modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)) {
                    Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "Map", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeskripsiScreenPreview() {
    CariLaundryTheme {
        DeskripsiScreen(laundryId = "1")
    }
}
