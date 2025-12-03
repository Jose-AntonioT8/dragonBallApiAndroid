package com.example.dragonballapiandroid.ui.creation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun CharacterCreationScreen(
    modifier : Modifier = Modifier,
    viewModel : CharacterCreationViewModel = hiltViewModel(),
    onNavegationBack:()->Unit
){
    Card(
        modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()                    .padding(start = 8.dp),
                value = viewModel.name,
                singleLine = true,
                isError = viewModel.isError,
                label = { Text("Nombre") },
                onValueChange = { viewModel.name = it }

            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()                    .padding(start = 8.dp),
                value = viewModel.ki,
                singleLine = true,
                isError = viewModel.isError,
                label = { Text("Ki") },
                onValueChange = { viewModel.ki = it }

            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()                    .padding(start = 8.dp),
                value = viewModel.maxKi,
                singleLine = true,
                isError = viewModel.isError,
                label = { Text("Max ki") },
                onValueChange = { viewModel.maxKi = it }

            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()                    .padding(start = 8.dp),
                value = viewModel.race,
                singleLine = true,
                isError = viewModel.isError,
                label = { Text("Raza") },
                onValueChange = { viewModel.race = it }

            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()                    .padding(start = 8.dp),
                value = viewModel.affiliation,
                singleLine = true,
                isError = viewModel.isError,
                label = { Text("Affiliacion") },
                onValueChange = { viewModel.affiliation = it }

            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()                    .padding(start = 8.dp),
                value = viewModel.gender,
                singleLine = true,
                isError = viewModel.isError,
                label = { Text("Genero") },
                onValueChange = { viewModel.gender = it }

            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 8.dp).fillMaxWidth(),
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