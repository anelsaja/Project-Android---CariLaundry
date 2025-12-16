package com.example.carilaundry.ui.feature.customer.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carilaundry.R
import com.example.carilaundry.ui.theme.CariLaundryTheme

// ================= DATA MODEL =================
data class FavoriteLaundry(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val imageRes: Int
)

// ================= SCREEN =================
@Composable
fun CustomerFavoriteScreen(
    onBack: () -> Unit = {},
    onItemClick: (String) -> Unit = {}
) {
    val favoriteList = listOf(
        FavoriteLaundry("1", "Laundry Wertwer", "Jalan Senopati No. 3", "+62 813-2707-4781", R.drawable.icon),
        FavoriteLaundry("2", "Laundry Bersih", "Jalan Mawar No. 10", "+62 812-3333-4444", R.drawable.icon),
        FavoriteLaundry("3", "Cuci Kilat", "Jalan Melati No. 5", "+62 811-5555-6666", R.drawable.icon)
    )

    Scaffold(
        containerColor = Color(0xFFE0F7FA)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            // HEADER
            FavoriteHeader(onBack = onBack)

            // TITLE
            Text(
                text = "Favorit",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D1B2A), // ⬅️ TAMBAH
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
            )


            // LIST
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoriteList) { laundry ->
                    FavoriteItemCard(
                        laundry = laundry,
                        onClick = { onItemClick(laundry.id) }
                    )
                }
            }
        }
    }
}

// ================= HEADER =================
@Composable
fun FavoriteHeader(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = "Back",
            tint = Color(0xFF0D1B2A), // ⬅️ TAMBAH
            modifier = Modifier
                .size(24.dp)
                .clickable { onBack() }
        )

        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo",
            modifier = Modifier
                .padding(start = 16.dp)
                .size(40.dp)
        )

        Text(
            text = "CariLaundry",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D1B2A), // ⬅️ TAMBAH
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}


// ================= ITEM CARD =================
@Composable
fun FavoriteItemCard(
    laundry: FavoriteLaundry,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(id = laundry.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFF3F7EC2))
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = laundry.name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.outline_favorite_24),
                        contentDescription = "Favorite",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = laundry.address,
                    color = Color(0xFFE0E0E0),
                    fontSize = 10.sp,
                    maxLines = 1,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_local_phone_24),
                        contentDescription = null,
                        tint = Color(0xFFE0E0E0),
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = " ${laundry.phone}",
                        color = Color(0xFFE0E0E0),
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

// ================= PREVIEW =================
@Preview(showBackground = true)
@Composable
fun FavoritePreview() {
    CariLaundryTheme {
        CustomerFavoriteScreen()
    }
}
