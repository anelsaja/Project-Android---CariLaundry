package com.example.carilaundry.ui.feature.owner.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carilaundry.R
import com.example.carilaundry.ui.theme.*

@Composable
fun OwnerRegisterScreen(
    modifier: Modifier = Modifier,
    onRegister: (businessName: String, ownerName: String, email: String, phone: String, address: String, password: String) -> Unit = { _, _, _, _, _, _ -> },
    onSignInClicked: () -> Unit = {}
) {
    // State Variables
    val scrollState = rememberScrollState()
    var businessName by remember { mutableStateOf("") }
    var ownerName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(scrollState) // Scroll penting karena form owner panjang
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // LOGO
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo",
            modifier = Modifier
                .size(120.dp) // Sedikit diperkecil agar muat di layar kecil karena form panjang
                .padding(bottom = 24.dp)
        )

        // JUDUL
        Text(
            text = "Daftar Owner",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = OnBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Lengkapi data usaha laundry Anda",
            fontSize = 14.sp,
            color = OnBackground.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- FORM INPUTS ---

        // Nama Usaha
        OutlinedTextField(
            value = businessName,
            onValueChange = { businessName = it },
            label = { Text("Nama Usaha", color = OnSurface) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Nama Pemilik
        OutlinedTextField(
            value = ownerName,
            onValueChange = { ownerName = it },
            label = { Text("Nama Pemilik", color = OnSurface) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email", color = OnSurface) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // No Telepon
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("No. Telepon", color = OnSurface) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Alamat
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Alamat (Opsional)", color = OnSurface) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = OnSurface) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Konfirmasi Password
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Konfirmasi Password", color = OnSurface) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Error Message Display
        errorMessage?.let { msg ->
            Text(
                text = msg,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // BUTTON DAFTAR
        Button(
            onClick = {
                errorMessage = null
                when {
                    businessName.isBlank() -> errorMessage = "Nama usaha wajib diisi"
                    ownerName.isBlank() -> errorMessage = "Nama pemilik wajib diisi"
                    email.isBlank() -> errorMessage = "Email wajib diisi"
                    password.length < 6 -> errorMessage = "Password minimal 6 karakter"
                    password != confirmPassword -> errorMessage = "Password tidak cocok"
                    else -> {
                        onRegister(businessName, ownerName, email, phone, address, password)
                    }
                }
            },
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
                text = "Daftar sebagai Owner",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // SUDAH PUNYA AKUN?
        TextButton(onClick = onSignInClicked) {
            Text(
                text = "Sudah punya akun? Masuk",
                color = Primary,
                fontWeight = FontWeight.Medium
            )
        }

        // Spacer bawah agar scroll bisa mentok nyaman
        Spacer(modifier = Modifier.height(24.dp))
    }
}

// Helper function agar coding lebih rapi dan tidak perlu copas warna berkali-kali
@Composable
fun customTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = OnSurface,
    unfocusedTextColor = OnSurface,
    focusedBorderColor = Primary,
    unfocusedBorderColor = Outline,
    focusedLabelColor = Primary,
    unfocusedLabelColor = OnSurface,
    cursorColor = Primary
)

@Preview(showBackground = true)
@Composable
fun OwnerRegisterPreview() {
    CariLaundryTheme {
        OwnerRegisterScreen()
    }
}