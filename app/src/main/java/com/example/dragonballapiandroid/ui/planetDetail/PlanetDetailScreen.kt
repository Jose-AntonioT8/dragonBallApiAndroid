package com.example.dragonballapiandroid.ui.planetDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import kotlin.Unit

@Composable
fun PlanetDetailScreen(
    modifier : Modifier = Modifier,
    viewModel : PlanetDetailViewModel = hiltViewModel(),
    onNavegationBack:()->Unit,
    onUpdatePlanet: (Long) -> Unit,

    ){
    val uiState by viewModel.uiState.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        PlanetCard(

            modifier = modifier.weight(1f),
            name = uiState.name,
            id = uiState.id,
            image = uiState.image,
            isDestroyed = uiState.isDestroyed,
            description = uiState.description
        )
        Button( modifier = Modifier.fillMaxWidth(), onClick = {
            onUpdatePlanet(uiState.id)
        }
        )
        {Text("Modificar planeta") }
        Button( modifier = Modifier.fillMaxWidth(), onClick = {
            viewModel.delete(uiState.id)
            onNavegationBack()
        }
        )
        {Text("Borrar planeta") }
        Button(
            onClick={
                onNavegationBack()
            },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Volver atrás")
        }
    }


}


@Composable
fun PlanetCard(
    modifier: Modifier,

    name : String,
    id : Long,
    image : String,
    isDestroyed : Boolean,
    description  : String
){
    Card(
        modifier = modifier
            .fillMaxWidth()

            .padding(8.dp)
    ){
        Column(modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())){
            Text(text="Id: $id", Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Row{
                AsyncImage(
                    model = image,
                    contentDescription = name,
                    modifier = Modifier
                        .size(width = 220.dp, height = 340.dp)
                )
                Column {
                    Text(text="Is destroyed: $isDestroyed")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text= name, Modifier.padding(start = 10.dp))
                }

            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(text= description, Modifier.padding(start = 10.dp, end = 10.dp))

        }
    }

}
