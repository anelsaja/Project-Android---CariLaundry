package com.example.carilaundry.ui.feature.customer.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carilaundry.R
import com.example.carilaundry.ui.theme.*

@Composable
fun CustomerLoginScreen(
    viewModel: CustomerLoginViewModel = viewModel(), // Inject ViewModel
    onLoginSuccess: () -> Unit = {}, // Callback jika sukses
    onRegisterClicked: () -> Unit = {},
    onSwitchToOwner: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Efek Samping: Cek jika login sukses, lalu navigasi
    LaunchedEffect(uiState.isLoginSuccess) {
        if (uiState.isLoginSuccess) {
            Toast.makeText(context, "Login Berhasil!", Toast.LENGTH_SHORT).show()
            onLoginSuccess()
            viewModel.resetState() // Opsional: Reset form
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // LOGO
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo",
            modifier = Modifier
                .size(140.dp)
                .padding(bottom = 32.dp)
        )

        // JUDUL
        Text(
            text = "Masuk Customer",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = OnBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Gunakan email dan password anda",
            fontSize = 14.sp,
            color = OnBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // EMAIL INPUT
        OutlinedTextField(
            value = uiState.email, // Ambil dari ViewModel
            onValueChange = { viewModel.onEmailChange(it) }, // Kirim ke ViewModel
            label = { Text("Email", color = OnSurface) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null, // Merah jika error
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
            value = uiState.password, // Ambil dari ViewModel
            onValueChange = { viewModel.onPasswordChange(it) }, // Kirim ke ViewModel
            label = { Text("Password", color = OnSurface) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null, // Merah jika error
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

        // PESAN ERROR (Muncul jika ada)
        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp).align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // BUTTON LOGIN
        Button(
            onClick = { viewModel.login() }, // Panggil fungsi Login di ViewModel
            enabled = !uiState.isLoading,    // Disable tombol saat loading
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = OnPrimary
            )
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    color = OnPrimary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = "Masuk",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
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

        // SWITCH ROLE
        TextButton(onClick = onSwitchToOwner) {
            Text(
                text = "Masuk sebagai Owner",
                color = Primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomerLoginPreview() {
    CariLaundryTheme {
        CustomerLoginScreen()
    }
}