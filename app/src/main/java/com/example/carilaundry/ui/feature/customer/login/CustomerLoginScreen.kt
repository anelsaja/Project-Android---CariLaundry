package com.example.carilaundry.ui.feature.customer.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
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
fun CustomerLoginScreen(
    modifier: Modifier = Modifier,
    onLogin: (email: String, password: String) -> Unit = { _, _ -> },
    onRegisterClicked: () -> Unit = {},
    onSwitchToOwner: () -> Unit = {}
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Masuk Customer", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Masuk untuk memesan layanan laundry.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
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

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { onLogin(email.value, password.value) }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Masuk")
            }



            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onRegisterClicked, modifier = Modifier.wrapContentWidth()) {
                Text(text = "Belum punya akun? Daftar")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = onSwitchToOwner, modifier = Modifier.wrapContentWidth()) {
                Text(text = "Masuk sebagai Owner")

            }
            
//            Button(onClick = )

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
