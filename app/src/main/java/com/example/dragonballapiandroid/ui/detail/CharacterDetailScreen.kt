package com.example.dragonballapiandroid.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun CharacterDetailScreen(
    modifier : Modifier = Modifier,
    viewModel : CharacterDetailViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    PokemonDetailScreen(
        modifier = modifier,
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
                modifier = modifier.fillMaxSize()
            ){
                Column(modifier = modifier.fillMaxSize()){
                    Text(text= name, )
                    Row{
                    }
            }
        }
}





/*
data class Character (
    val id : Long,
    val name : String,
    val ki:String,
    val maxKi:String,
    val race:String,
    val gender:String,
    val description:String,
    val image:String,
    val affiliation:String
    )
 */