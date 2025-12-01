package com.example.dragonballapiandroid.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import kotlin.Unit

@Composable
fun CharacterDetailScreen(
    modifier : Modifier = Modifier,
    viewModel : CharacterDetailViewModel = hiltViewModel(),
    onNavegationBack:()->Unit,
){
    val uiState by viewModel.uiState.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        PokemonDetailScreen(

            modifier = modifier.weight(1f),
            name = uiState.name,
            id = uiState.id,
            image = uiState.image,
            ki = uiState.ki,
            maxKi = uiState.maxKi,
            race = uiState.race,
            gender = uiState.gender,
            affiliation = uiState.affiliation,
            description = uiState.description
        )
        Button( modifier = Modifier.fillMaxWidth(), onClick = {viewModel.delete(uiState.id)}) {Text("Borrar personaje") }
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
fun PokemonDetailScreen(
    modifier: Modifier,

    name : String,
    id : Long,
    image : String,
    ki : String,
    maxKi : String,
    race : String,
    gender : String,
    affiliation  : String,
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
                    Text(text= name, Modifier.padding(start = 10.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Row{
                        AsyncImage(
                            model = image,
                            contentDescription = name,
                            modifier = Modifier
                                .size(width = 220.dp, height = 340.dp)
                        )
                        Column {
                            Text(text="Ki: $ki")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text="Max Ki: $maxKi")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Raza: $race")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Género: $gender")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Afiliación: $affiliation")
                        }

                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text= description, Modifier.padding(start = 10.dp, end = 10.dp))

            }
        }

}