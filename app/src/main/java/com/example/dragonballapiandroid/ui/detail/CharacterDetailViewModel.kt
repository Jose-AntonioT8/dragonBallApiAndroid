package com.example.dragonballapiandroid.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.dragonballapiandroid.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import androidx.navigation.toRoute
import com.example.dragonballapiandroid.ui.navegation.Route
import androidx.lifecycle.viewModelScope
import com.example.dragonballapiandroid.data.model.Character

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class DetailUiState(
    val id : Long = 0,
    val name : String = "",
    val ki : String="",
    val image : String = "",
    val description : String = "",
    val maxKi:String ="",
    val race:String="",
    val gender:String="",
    val affiliation:String=""
)

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val characterRepository : CharacterRepository
): ViewModel() {
    private val _uiState : MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState())
    val uiState : StateFlow<DetailUiState>
        get()= _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            val route = savedStateHandle.toRoute<Route.Detail>()
            val characterId = route.id
            val character = characterRepository.readOne(characterId)
            character?.let{
                _uiState.value = character.toDetailUiState()
        }
        }
    }

}

fun Character.toDetailUiState(): DetailUiState = DetailUiState(
    name = this.name,
    id = this.id,
    image = this.image,
    ki = this.ki,
    maxKi = this.maxKi,
    race = this.race,
    gender = this.gender,
    affiliation = this.affiliation,
    description = this.description,
)
