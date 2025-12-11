package com.example.carilaundry.ui.feature.customer.notifikasi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carilaundry.ui.theme.CariLaundryTheme
import com.example.carilaundry.ui.theme.Primary

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val time: String
)

@Composable
fun NotifikasiCustomerScreen(
    modifier: Modifier = Modifier,
    items: List<NotificationItem> = sampleNotifications(),
    onBack: () -> Unit = {},
    onDetailClick: (String) -> Unit = {}
) {
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = "CariLaundry", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Notifikasi", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(12.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(items) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onDetailClick(item.id) },
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(containerColor = Primary),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween) {

                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = item.title, style = MaterialTheme.typography.titleMedium, color = Color.White, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = item.message, style = MaterialTheme.typography.bodySmall, color = Color.White, maxLines = 2, overflow = TextOverflow.Ellipsis)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = item.time, style = MaterialTheme.typography.bodySmall, color = Color(0xFFCCE6FF))
                            }

                            Spacer(modifier = Modifier.size(8.dp))

                            OutlinedButton(
                                onClick = { /* detail pesanan londri*/},
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                                border = ButtonDefaults.outlinedButtonBorder(enabled = true),
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.small)
                            ) {
                                Text(text = "Detail", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun sampleNotifications(): List<NotificationItem> = listOf(
    NotificationItem("1", "Mas Anel", "Melakukan Orderan Pada Laundry xxx", "1 jam yang lalu"),
    NotificationItem("2", "Mas Anel", "Melakukan Orderan Pada Laundry yyy", "2 jam yang lalu"),
    NotificationItem("3", "Mas Anel", "Melakukan Orderan Pada Laundry zzz", "5 jam yang lalu"),
    NotificationItem("4", "Mas Anel", "Melakukan Orderan Pada Laundry abc", "1 hari yang lalu"),
    NotificationItem("5", "Mas Anel", "Melakukan Orderan Pada Laundry def", "2 hari yang lalu")
)

@Preview(showBackground = true)
@Composable
fun NotifikasiCustomerPreview() {
    CariLaundryTheme {
        NotifikasiCustomerScreen()
    }
}
