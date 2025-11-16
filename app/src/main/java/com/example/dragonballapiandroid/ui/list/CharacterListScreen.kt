package com.example.dragonballapiandroid.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage


@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel= hiltViewModel(),
    modifier: Modifier,
    onShowDetail:(Long)->Unit
){
    val uiState by viewModel.uiState.collectAsState()
    when(uiState){
        is ListUiState.Loading ->{
            listLoading(modifier)
        }

        ListUiState.Initial -> {

        }
        is ListUiState.Succes -> {

            CharacterListScreen(
                modifier, uiState, onShowDetail, viewModel
            )
        }

        is ListUiState.buscar -> {
            CharacterListScreen( modifier, uiState, onShowDetail, viewModel
            )
        }

        is ListUiState.Error -> {
            CharacterListScreen( modifier, uiState, onShowDetail, viewModel, (uiState as ListUiState.Error).message
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun listLoading(
    modifier : Modifier
){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadingIndicator(
            modifier = modifier.size(130.dp)
        )
    }
}
@Composable
fun CharacterListScreen(
    modifier : Modifier,
    uiState: ListUiState,
    onShowDetail: (Long) -> Unit,
    viewModel:CharacterListViewModel,
    error:String?=null,
){
    Column(
        modifier = modifier.fillMaxWidth(),

    ){
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical =8.dp).fillMaxWidth(),
            verticalAlignment =Alignment.CenterVertically) {
            Text("Pagina: ")
            OutlinedTextField(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp).size(
                    width = 100.dp,
                    height = 30.dp
                ),
                state = viewModel.busquedaParametros,
            )
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    viewModel.search()
                }
            ){
                Text("Ir")
            }
            Text(text = error ?: "")

        }
    }

    LazyColumn(
        modifier = modifier.padding(top=140.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        items(
            items = (uiState as ListUiState.Succes).characters,
            key = { item ->
                item.id
            }
        ){
            Card(
                modifier = Modifier.fillMaxWidth()
                    .padding(8.dp)
                .clickable(enabled = true,
            onClick = {
                onShowDetail(it.id)
            }),
                elevation = CardDefaults.cardElevation(6.dp)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    AsyncImage(
                        contentDescription = it.name,
                        model = it.image,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Column {
                        Text(text="id: "+ it.id)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text="Nombre: "+ it.name)

                    }
                }
            }
        }
    }
}
