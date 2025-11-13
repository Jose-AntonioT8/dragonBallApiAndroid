package com.example.dragonballapiandroid.ui.common
import androidx.navigation.NavBackStackEntry
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    backStackEntry: NavBackStackEntry?=null
) {
    TopAppBar(
        title = {
            Text("Api Dragon Ball")
        }
    )
}