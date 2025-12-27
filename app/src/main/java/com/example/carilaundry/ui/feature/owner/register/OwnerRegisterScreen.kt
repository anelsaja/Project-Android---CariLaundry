package com.example.carilaundry.ui.feature.owner.register

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carilaundry.R
import com.example.carilaundry.ui.theme.*

@Composable
fun OwnerRegisterScreen(
    modifier: Modifier = Modifier,
    // Inject ViewModel
    viewModel: OwnerRegisterViewModel = viewModel(),
    // Callback Navigasi
    onRegisterSuccess: () -> Unit = {},
    onSignInClicked: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // Handle Success Navigation
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            Toast.makeText(context, "Registrasi Owner Berhasil!", Toast.LENGTH_SHORT).show()
            onRegisterSuccess()
            viewModel.resetState()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(scrollState) // Scroll penting
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // LOGO
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo",
            modifier = Modifier
                .size(120.dp)
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

        // --- FORM INPUTS (Terhubung ke ViewModel) ---

        // Nama Usaha
        OutlinedTextField(
            value = uiState.businessName,
            onValueChange = { viewModel.onBusinessNameChange(it) },
            label = { Text("Nama Usaha", color = OnSurface) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Nama Pemilik
        OutlinedTextField(
            value = uiState.ownerName,
            onValueChange = { viewModel.onOwnerNameChange(it) },
            label = { Text("Nama Pemilik", color = OnSurface) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Email
        OutlinedTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email", color = OnSurface) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // No Telepon
        OutlinedTextField(
            value = uiState.phone,
            onValueChange = { viewModel.onPhoneChange(it) },
            label = { Text("No. Telepon", color = OnSurface) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Alamat
        OutlinedTextField(
            value = uiState.address,
            onValueChange = { viewModel.onAddressChange(it) },
            label = { Text("Alamat (Opsional)", color = OnSurface) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Password
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Password", color = OnSurface) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Konfirmasi Password
        OutlinedTextField(
            value = uiState.confirmPassword,
            onValueChange = { viewModel.onConfirmPasswordChange(it) },
            label = { Text("Konfirmasi Password", color = OnSurface) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = customTextFieldColors()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Error Message Display
        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

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
                CircularProgressIndicator(color = OnPrimary, modifier = Modifier.size(24.dp))
            } else {
                Text(
                    text = "Daftar sebagai Owner",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
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

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// Helper Colors
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