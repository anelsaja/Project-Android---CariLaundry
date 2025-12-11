package com.example.carilaundry.ui.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.carilaundry.ui.components.RoleCard
import com.example.carilaundry.ui.theme.CariLaundryTheme

@Composable
fun RoleSelectionScreen(
    modifier: Modifier = Modifier,
    onSelectCustomer: () -> Unit,
    onSelectOwner: () -> Unit,
    onSignInClicked: () -> Unit
) {
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Daftar", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Pilih peran Anda untuk memulai. Anda dapat mendaftar sebagai Customer untuk mencari layanan atau Owner untuk mengelola layanan Anda.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(24.dp))

            RoleCard(
                icon = Icons.Filled.Person,
                title = "Customer",
                description = "Cari dan pilih layanan laundry terbaik di dekat Anda.",
                modifier = Modifier.fillMaxWidth(),
                onClick = onSelectCustomer
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoleCard(
                icon = Icons.Filled.Person,
                title = "Owner",
                description = "Daftarkan tempat laundry Anda dan kelola pesanan dengan mudah.",
                modifier = Modifier.fillMaxWidth(),
                onClick = onSelectOwner
            )

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onSignInClicked) {
                Text(text = "Sudah punya akun? Masuk")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoleSelectionPreview() {
    CariLaundryTheme {
        RoleSelectionScreen(
            onSelectCustomer = {},
            onSelectOwner = {},
            onSignInClicked = {}
        )
    }
}
