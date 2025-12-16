package com.example.carilaundry.ui.feature.owner.profil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carilaundry.R
import com.example.carilaundry.ui.theme.CariLaundryTheme

// ================= SCREEN =================
@Composable
fun OwnerProfilScreen(
    onBack: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    // Menggunakan palet warna yang sama dengan Customer
    val BackgroundColor = Color(0xFFE0F7FA)
    val InfoTextColor = Color(0xFF5C6BC0)
    val NameColor = Color(0xFF1A237E)
    val AvatarColor = Color(0xFF6495ED)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {

        // HEADER
        OwnerProfileHeader(onBack)

        Text(
            text = "Profil Owner",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(start = 20.dp, bottom = 16.dp)
        )

        // === CARD PROFIL ===
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    // Avatar Inisial
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(AvatarColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "PB", // Inisial (Pak Budi)
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Nama & Role
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text(
                            text = "Pak Budi",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = NameColor
                        )
                        Text(
                            text = "Pemilik Laundry",
                            fontSize = 12.sp,
                            color = InfoTextColor
                        )
                    }
                }

                // Detail Informasi Owner
                ProfileDetailRow(
                    iconRes = R.drawable.baseline_email_24,
                    text = "budi.laundry@email.com",
                    color = InfoTextColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                ProfileDetailRow(
                    iconRes = R.drawable.baseline_local_phone_24,
                    text = "+62 812-9876-5432",
                    color = InfoTextColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                ProfileDetailRow(
                    iconRes = R.drawable.outline_location_on_24,
                    text = "Jalan Senopati No. 3, Kampungin",
                    color = InfoTextColor
                )
            }
        }

        // === LOGOUT ===
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clickable { onLogout() }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_logout_24),
                    contentDescription = "Logout",
                    tint = Color.Black
                )
                Text(
                    text = "Keluar",
                    color = Color.Black,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}


// ================= KOMPONEN HELPER =================

@Composable
fun OwnerProfileHeader(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = "Back",
            tint = Color(0xFF0D1B2A),
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
            text = "CariLaundry Owner",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0D1B2A),
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun ProfileDetailRow(iconRes: Int, text: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = text,
            color = color,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OwnerProfilPreview() {
    CariLaundryTheme {
        OwnerProfilScreen()
    }
}