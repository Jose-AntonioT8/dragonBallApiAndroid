package com.example.dragonballapiandroid.ui.list

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
fun CharacterListScreen(
    onShowDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        SearchBar(
            viewModel = viewModel,
            isError = uiState is ListUiState.Error
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
                CharacterList(
                    characters = currentState.characters,
                    onShowDetail = onShowDetail
                )
            }
        }
    }
}


@Composable
private fun SearchBar(viewModel: CharacterListViewModel, isError: Boolean) {
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
private fun CharacterList(
    characters: List<ListItemUiState>,
    onShowDetail: (Long) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = characters,
            key = { character -> character.id }
        ) { character ->
            Card(
                modifier = Modifier.padding(8.dp)
                    .fillMaxWidth()
                    .clickable { onShowDetail(character.id) },
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AsyncImage(
                        contentDescription = character.name,
                        model = character.image,
                        modifier = Modifier.size(60.dp)
                    )
                    Column {
                        Text(text = "Id: ${character.id}")
                        Text(text = "Nombre: ${character.name}")
                    }
                }
            }
        }
    }
}

