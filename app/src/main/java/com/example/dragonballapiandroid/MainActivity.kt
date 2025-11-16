package com.example.dragonballapiandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dragonballapiandroid.ui.navegation.NavGraph
import com.example.dragonballapiandroid.ui.theme.DragonballApiAndroidTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DragonballApiAndroidTheme {
                NavGraph()
            }
        }
    }
}

