package com.example.carilaundry.ui.feature.customer.register

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
fun CustomerRegisterScreen(
    // Inject ViewModel
    viewModel: CustomerRegisterViewModel = viewModel(),
    // Callback ketika register sukses (biasanya diarahkan ke Login atau Home)
    onRegisterSuccess: () -> Unit = {},
    onLoginClicked: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Efek Samping: Navigasi jika sukses
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            Toast.makeText(context, "Registrasi Berhasil! Silakan Masuk.", Toast.LENGTH_SHORT).show()
            onRegisterSuccess()
            viewModel.resetState()
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
            text = "Daftar Customer",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = OnBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Buat akun baru untuk mulai menggunakan layanan",
            fontSize = 14.sp,
            color = OnBackground.copy(alpha = 0.7f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // NAMA
        OutlinedTextField(
            value = uiState.name,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Nama", color = OnSurface) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null,
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

        // EMAIL
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email", color = OnSurface) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null,
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

        // PASSWORD
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Password", color = OnSurface) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null,
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

        // Error Message Text
        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp).align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // BUTTON DAFTAR
        Button(
            onClick = { viewModel.register() },
            enabled = !uiState.isLoading,
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
                    text = "Daftar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // SUDAH PUNYA AKUN?
        TextButton(onClick = onLoginClicked) {
            Text(
                text = "Sudah punya akun? Masuk",
                color = Primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomerRegisterPreview() {
    CariLaundryTheme {
        CustomerRegisterScreen()
    }
}