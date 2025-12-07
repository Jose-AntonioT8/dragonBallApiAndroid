package com.example.dragonballapiandroid.ui.planetUpdate


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.dragonballapiandroid.data.model.Planet
import com.example.dragonballapiandroid.data.repository.PlanetRepository
import com.example.dragonballapiandroid.ui.planetDetail.DetailUiState
import com.example.dragonballapiandroid.ui.planetDetail.toDetailUiState
import com.example.dragonballapiandroid.ui.navegation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class DetailUiState(
    val id : Long = 0,
    val name : String = "",
    val isDestroyed: Boolean = false,
    val image : String = "",
    val description : String = "",
)

@HiltViewModel
class PlanetUpdateViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val planetRepository:PlanetRepository
): ViewModel() {
    var isError =false
    var planetId = 0L
    var isDestroyed by mutableStateOf(false)
    var name by mutableStateOf("")
    var description by mutableStateOf("")
    private var characterImage: String = ""
    private val _uiState : MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState())
    val uiState : StateFlow<DetailUiState> = _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            val route = savedStateHandle.toRoute<Route.Detail>()
            planetId = route.id
            val character = planetRepository.readOne(planetId)
            character.let{character ->
                name = character.getOrNull()!!.name
                description = character.getOrNull()!!.description
                isDestroyed = character.getOrNull()!!.isDestroyed

                characterImage = character.getOrNull()!!.image

                _uiState.value = character.getOrNull()!!.toDetailUiState()
            }


        }
    }
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        isError = true

    }
    fun update(){
        viewModelScope.launch {
            val planet = Planet(
                id = planetId,
                name = name,
                description = description,
                image = characterImage,
                isDestroyed = isDestroyed
            )
            planetRepository.insert(planet)
        }
    }



}

