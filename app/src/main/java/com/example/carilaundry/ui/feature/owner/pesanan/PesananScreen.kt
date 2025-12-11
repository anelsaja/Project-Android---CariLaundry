package com.example.carilaundry.ui.feature.owner.pesanan



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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carilaundry.ui.theme.CariLaundryTheme
import com.example.carilaundry.ui.components.BottomNavBar

data class Order(
    val customerName: String,
    val address: String,
    val service: String,
    val weightEstimate: String,
    val priceText: String
)

@Composable
fun OwnerOrdersScreen(
    modifier: Modifier = Modifier,
    orders: List<Order> = remember {
        List(5) {
            Order(
                customerName = "Mas Anel",
                address = "Jalan Senopati No. 3, Kampungin",
                service = "Layanan: Cuci & Lipat",
                weightEstimate = "Estimasi Berat: 3kg",
                priceText = "Rp 24.000,-"
            )
        }
    },
    onDetailClick: (Order) -> Unit = {},
    currentRoute: String = "orders",
    onNavigate: (String) -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Pesanan",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(orders) { order ->
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Avatar circle
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.9f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = order.customerName.split(" ").firstOrNull() ?: "U",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            Spacer(modifier = Modifier.padding(8.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = order.customerName,
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                                    color = Color.White
                                )
                                Text(
                                    text = order.address,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = order.service,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White
                                )
                                Text(
                                    text = order.weightEstimate,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                OutlinedButton(
                                    onClick = { onDetailClick(order) },
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier
                                ) {
                                    Text(text = "Detail", color = Color.White)
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = order.priceText,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFF00C853) // green accent
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun OwnerOrdersPreview() {
    CariLaundryTheme {
        OwnerOrdersScreen()
    }
}