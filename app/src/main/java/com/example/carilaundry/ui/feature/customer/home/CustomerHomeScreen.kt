package com.example.carilaundry.ui.feature.customer.home

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carilaundry.R
import com.example.carilaundry.domain.model.Laundry
import com.example.carilaundry.ui.AppViewModelProvider
import com.example.carilaundry.ui.theme.Background
import com.example.carilaundry.ui.theme.OnBackground
import com.example.carilaundry.ui.theme.OnPrimary
import com.example.carilaundry.ui.theme.Primary

// ================== SCREEN UTAMA ==================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onItemClick: (String) -> Unit = {},
    onOpenFavorites: () -> Unit = {},
    onOpenNotifications: () -> Unit = {},
    onOpenProfile: () -> Unit = {}
) {
    val context = LocalContext.current
    
    // Perbaikan: Gunakan collectAsStateWithLifecycle untuk mengambil state
    val homeUiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    var searchText by remember { mutableStateOf("") }

    // --- IZIN LOKASI ---
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Izin lokasi diberikan", Toast.LENGTH_SHORT).show()
            // Di sini nanti bisa panggil fungsi untuk ambil lokasi terkini
        } else {
            Toast.makeText(context, "Izin lokasi ditolak, fitur peta mungkin terbatas", Toast.LENGTH_SHORT).show()
        }
    }

    // Cek izin saat pertama kali layar dibuka
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Scaffold(
        containerColor = Background,
        modifier = modifier
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            // 1. Header & Search Bar
            HomeHeader(
                onFavoriteClick = onOpenFavorites,
                onNotifClick = onOpenNotifications,
                onProfileClick = onOpenProfile
            )

            SearchBar(
                value = searchText,
                onValueChange = { searchText = it }
            )

            Text(
                text = "Laundry terdekat",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = OnBackground,
                modifier = Modifier.padding(16.dp)
            )

            // 2. Konten List Laundry
            when (val state = homeUiState) {
                is HomeUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Primary)
                    }
                }
                is HomeUiState.Success -> {
                    LaundryGrid(
                        laundryList = state.laundryList,
                        onItemClick = onItemClick
                    )
                }
                is HomeUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Gagal memuat data: ${state.message}", color = Color.Red)
                    }
                }
            }
        }
    }
}

// ================== GRID SECTION ==================
@Composable
fun LaundryGrid(
    laundryList: List<Laundry>,
    onItemClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(laundryList) { item ->
            LaundryGridItem(
                item = item,
                onClick = { onItemClick(item.id) }
            )
        }
    }
}

// ================== HEADER ==================
@Composable
fun HomeHeader(
    onFavoriteClick: () -> Unit,
    onNotifClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo",
            modifier = Modifier.size(40.dp)
        )

        Text(
            text = "CariLaundry",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = OnBackground,
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        )

        Icon(
            painter = painterResource(id = R.drawable.outline_favorite_24),
            contentDescription = "Favorite",
            tint = OnBackground,
            modifier = Modifier
                .size(24.dp)
                .clickable { onFavoriteClick() }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            painter = painterResource(id = R.drawable.outline_notifications_24),
            contentDescription = "Notification",
            tint = OnBackground,
            modifier = Modifier
                .size(24.dp)
                .clickable { onNotifClick() }
        )

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            painter = painterResource(id = R.drawable.outline_account_circle_24),
            contentDescription = "Profile",
            tint = OnBackground,
            modifier = Modifier
                .size(24.dp)
                .clickable { onProfileClick() }
        )
    }
}

// ================== SEARCH BAR ==================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                "Cari Tempat Laundry...",
                color = Color.Black.copy(alpha = 0.5f)
            )
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp),
        shape = RoundedCornerShape(25.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color(0xFFCFD8DC),
            unfocusedIndicatorColor = Color(0xFFCFD8DC),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = Primary
        )
    )
}

// ================== GRID ITEM ==================
@Composable
fun LaundryGridItem(
    item: Laundry,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Primary)
                    .padding(12.dp)
            ) {
                Text(
                    text = item.name,
                    color = OnPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1
                )
                Text(
                    text = item.address,
                    color = OnPrimary.copy(alpha = 0.8f),
                    fontSize = 10.sp,
                    maxLines = 1
                )
                Text(
                    text = item.distance,
                    color = OnPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }
    }
}
