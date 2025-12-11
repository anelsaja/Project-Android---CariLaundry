package com.example.carilaundry.ui.feature.customer.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carilaundry.ui.theme.CariLaundryTheme
import com.example.carilaundry.ui.theme.Primary

data class Laundry(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val distance: String
)

@Composable
fun CustomerHomeScreen(
    modifier: Modifier = Modifier,
    items: List<Laundry> = sampleData(),
    onItemClick: (String) -> Unit = {},
    onOpenFavorites: () -> Unit = {},
    onOpenNotifications: () -> Unit = {},
    onOpenProfile: () -> Unit = {}
) {
    val query = remember { mutableStateOf("") }

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "CariLaundry", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                Row {
                    IconButton(onClick = onOpenFavorites) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites")
                    }
                    IconButton(onClick = onOpenNotifications) {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                    IconButton(onClick = onOpenProfile) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = query.value,
                onValueChange = { query.value = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Cari Tempat Laundry...") },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(text = "Laundry terdekat", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)

            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 80.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clickable { onItemClick(item.id) },
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .background(Color.Gray))

                            Box(modifier = Modifier
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(Primary)
                                .padding(12.dp)) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(text = item.name, style = MaterialTheme.typography.titleMedium, color = Color.White, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Column {
                                            Text(text = item.address, style = MaterialTheme.typography.bodySmall, color = Color.White, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                            Text(text = item.phone, style = MaterialTheme.typography.bodySmall, color = Color.White, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        }
                                        Text(text = item.distance, style = MaterialTheme.typography.bodySmall, color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun sampleData(): List<Laundry> = listOf(
    Laundry("1", "Laundry Wertwer", "Jalan Sempiont No. 3, Kampungin", "+62 813-2707-4781", "135 m"),
    Laundry("2", "Laundry Biru", "Jalan Mawar 10", "+62 812-0000-0000", "200 m"),
    Laundry("3", "Laundry Cepat", "Jalan Melati 5", "+62 811-1111-111", "85 m"),
    Laundry("4", "Laundry Bersih", "Jalan Kenanga 7", "+62 810-2222-222", "150 m")
)

@Preview(showBackground = true)
@Composable
fun CustomerHomePreview() {
    CariLaundryTheme {
        CustomerHomeScreen()
    }
}
