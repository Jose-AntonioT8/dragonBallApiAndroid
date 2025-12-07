package com.example.dragonballapiandroid.ui.planetList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage

@Composable
fun PlanetListScreen(
    onCreate:()->Unit,
    onShowDetail: (Long) -> Unit,
    onCharacterList:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: PlanetListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        SearchBar(
            viewModel = viewModel,
            onCreate = onCreate,
            onCharacterList = onCharacterList,
            isError = uiState is ListUiState.Error,

            )

        when (val currentState = uiState) {
            is ListUiState.Initial -> {
            }
            is ListUiState.Loading -> {
                ListLoading()
            }
            is ListUiState.Error -> {
                Text(
                    text = currentState.message,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            is ListUiState.Succes -> {
                PlanetList(
                    planets = currentState.planets,
                    onShowDetail = onShowDetail
                )
            }
        }
    }
}


@Composable
private fun SearchBar(viewModel: PlanetListViewModel,  onCharacterList:()->Unit,
                      onCreate:()->Unit,
                      isError: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Pagina: ")
        OutlinedTextField(
            modifier = Modifier
                .width(100.dp)
                .padding(start = 8.dp),
            value = viewModel.busquedaParametros,
            onValueChange = { nuevoTexto ->
                viewModel.onBusquedaChanged(nuevoTexto)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            isError = isError,
            label = { Text("Nº") }
        )
        Button(
            modifier = Modifier.padding(start = 8.dp),
            onClick = {
                viewModel.search()
            }
        ) {
            Text("Ir")
        }
        Button(
            modifier = Modifier.padding(start = 8.dp),
            onClick = {
                onCreate()

            }
        ) {
            Text("Crear Planet")
        }
        Button(
                modifier = Modifier.padding(start = 8.dp),
        onClick = {
            onCharacterList()

        }
        ) {
        Text("Planetas")
    }

    }
}


@Composable
private fun ListLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(100.dp))
    }
}


@Composable
private fun PlanetList(
    planets: List<ListItemUiState>,
    onShowDetail: (Long) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = planets,
            key = { Planet -> Planet.id }
        ) { Planet ->
            Card(
                modifier = Modifier.padding(8.dp)
                    .fillMaxWidth()
                    .clickable { onShowDetail(Planet.id) },
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AsyncImage(
                        contentDescription = Planet.name,
                        model = Planet.image,
                        modifier = Modifier.size(60.dp)
                    )
                    Column {
                        Text(text = "Id: ${Planet.id}")
                        Text(text = "Nombre: ${Planet.name}")
                    }
                }
            }
        }
    }
}

