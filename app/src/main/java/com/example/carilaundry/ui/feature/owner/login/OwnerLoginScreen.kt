package com.example.carilaundry.ui.feature.owner.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carilaundry.R
import com.example.carilaundry.ui.theme.*

@Composable
fun OwnerLoginScreen(
    onLogin: (email: String, password: String) -> Unit = { _, _ -> },
    onRegisterClicked: () -> Unit = {},
    onSwitchToCustomer: () -> Unit = {}
) {
    // Menggunakan 'by' agar tidak perlu menulis .value terus menerus
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background) // Menggunakan warna background tema
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Posisi elemen di tengah vertikal
    ) {

        // LOGO (Menggunakan logo yang sama)
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo",
            modifier = Modifier
                .size(140.dp)
                .padding(bottom = 32.dp)
        )

        // JUDUL OWNER
        Text(
            text = "Masuk Owner",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = OnBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        // SUBJUDUL KHUSUS OWNER
        Text(
            text = "Masuk untuk mengelola laundry Anda",
            fontSize = 14.sp,
            color = OnBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // EMAIL INPUT
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = OnSurface) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = OnSurface,
                unfocusedTextColor = OnSurface,
                focusedBorderColor = Primary,
                unfocusedBorderColor = Outline,
                focusedLabelColor = Primary,
                unfocusedLabelColor = OnSurface,
                cursorColor = Primary
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // PASSWORD INPUT
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = OnSurface) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = OnSurface,
                unfocusedTextColor = OnSurface,
                focusedBorderColor = Primary,
                unfocusedBorderColor = Outline,
                focusedLabelColor = Primary,
                unfocusedLabelColor = OnSurface,
                cursorColor = Primary
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // BUTTON LOGIN
        Button(
            onClick = { onLogin(email, password) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = OnPrimary
            )
        ) {
            Text(
                text = "Masuk",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // DAFTAR
        TextButton(onClick = onRegisterClicked) {
            Text(
                text = "Belum punya akun? Daftar",
                color = Primary,
                fontWeight = FontWeight.Medium
            )
        }

        // SWITCH ROLE (Ke Customer)
        TextButton(onClick = onSwitchToCustomer) {
            Text(
                text = "Masuk sebagai Customer",
                color = Primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OwnerLoginPreview() {
    CariLaundryTheme {
        OwnerLoginScreen()
    }
}