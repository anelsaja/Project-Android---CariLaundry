package com.example.carilaundry.ui.feature.owner.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carilaundry.ui.theme.CariLaundryTheme

@Composable
fun OwnerRegisterScreen(
    modifier: Modifier = Modifier,
    onRegister: (businessName: String, ownerName: String, email: String, phone: String, address: String, password: String) -> Unit = { _, _, _, _, _, _ -> },
    onSignInClicked: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val businessName = remember { mutableStateOf("") }
    val ownerName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Daftar sebagai Owner", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Buat akun untuk mengelola laundry Anda. Lengkapi informasi berikut.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = businessName.value,
                onValueChange = { businessName.value = it },
                label = { Text("Nama Usaha") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = ownerName.value,
                onValueChange = { ownerName.value = it },
                label = { Text("Nama Pemilik") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = phone.value,
                onValueChange = { phone.value = it },
                label = { Text("No. Telepon") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = address.value,
                onValueChange = { address.value = it },
                label = { Text("Alamat (opsional)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                label = { Text("Konfirmasi Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            errorMessage.value?.let { msg ->
                Text(text = msg, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    errorMessage.value = null
                    when {
                        businessName.value.isBlank() -> errorMessage.value = "Nama usaha wajib diisi"
                        ownerName.value.isBlank() -> errorMessage.value = "Nama pemilik wajib diisi"
                        email.value.isBlank() -> errorMessage.value = "Email wajib diisi"
                        password.value.length < 6 -> errorMessage.value = "Password minimal 6 karakter"
                        password.value != confirmPassword.value -> errorMessage.value = "Password tidak cocok"
                        else -> {
                            onRegister(
                                businessName.value,
                                ownerName.value,
                                email.value,
                                phone.value,
                                address.value,
                                password.value
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Daftar sebagai Owner")
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onSignInClicked, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Sudah punya akun? Masuk")
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
fun OwnerRegisterPreview() {
    CariLaundryTheme {
        OwnerRegisterScreen()
    }
}
