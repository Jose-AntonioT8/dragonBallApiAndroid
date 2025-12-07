package com.example.dragonballapiandroid.ui.planetCreation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun PlanetCreationScreen(
    modifier : Modifier = Modifier,
    viewModel : PlanetCreationViewModel = hiltViewModel(),
    onNavegationBack:()->Unit
){
    Card(
        modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                value = viewModel.name,
                singleLine = true,
                isError = viewModel.isError,
                label = { Text("Nombre") },
                onValueChange = { viewModel.name = it }

            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿Está destruido?",
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = viewModel.isDestroyed,
                    onCheckedChange = { viewModel.isDestroyed = it }
                )
            }
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth(),
                value = viewModel.description,
                singleLine = false,
                isError = viewModel.isError,
                label = { Text("Descripcion") },
                onValueChange = { viewModel.description = it }

            )
        }
        Row(modifier = Modifier.padding(8.dp)){
            Button(
                onClick={
                    viewModel.create()
                    onNavegationBack()
                },
            ){
                Text("Crear")
            }
            Button(
                onClick={
                    onNavegationBack()
                },
            ){
                Text("Cancelar")
            }
        }
    }
}