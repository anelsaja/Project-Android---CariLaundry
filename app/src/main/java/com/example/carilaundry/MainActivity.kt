package com.example.carilaundry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.carilaundry.ui.navigation.NavGraph
import com.example.carilaundry.ui.theme.CariLaundryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CariLaundryTheme {
                NavGraph()
            }
        }
    }
}

