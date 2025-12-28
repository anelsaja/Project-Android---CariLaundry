package com.example.carilaundry.ui.feature.customer.profil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// ================= SCREEN =================
@Composable
fun ProfilScreen(
    onBack: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val BackgroundColor = Color(0xFFE0F7FA)
    val InfoTextColor = Color(0xFF5C6BC0)
    val NameColor = Color(0xFF1A237E)
    val AvatarColor = Color(0xFF6495ED)

    // --- STATE DATA USER ---
    var name by remember { mutableStateOf("Memuat...") }
    var email by remember { mutableStateOf("...") }
    var phone by remember { mutableStateOf("-") } // Belum disimpan saat register
    var address by remember { mutableStateOf("-") } // Belum disimpan saat register
    var initial by remember { mutableStateOf("?") }

    // --- FETCH DATA DARI FIREBASE ---
    LaunchedEffect(Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            email = user.email ?: ""
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        name = document.getString("name") ?: "User"
                        // Phone & Address bisa diambil jika nanti sudah ada fitur edit profil
                        phone = document.getString("phone") ?: "Nomor belum diatur"
                        address = document.getString("address") ?: "Alamat belum diatur"

                        // Buat inisial (2 huruf pertama)
                        initial = if (name.length >= 2) name.take(2).uppercase() else name.take(1).uppercase()
                    }
                }
                .addOnFailureListener {
                    name = "Gagal memuat"
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {

        // HEADER
        ProfileHeader(onBack)

        Text(
            text = "Profil",
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
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(AvatarColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = initial,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = NameColor,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                ProfileDetailRow(
                    iconRes = R.drawable.baseline_email_24,
                    text = email,
                    color = InfoTextColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                ProfileDetailRow(
                    iconRes = R.drawable.baseline_local_phone_24,
                    text = phone,
                    color = InfoTextColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                ProfileDetailRow(
                    iconRes = R.drawable.outline_location_on_24,
                    text = address,
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


// ================= KOMPONEN =================

@Composable
fun ProfileHeader(onBack: () -> Unit) {
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
            text = "CariLaundry",
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
fun ProfilPreview() {
    CariLaundryTheme {
        ProfilScreen()
    }
}
