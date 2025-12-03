package com.example.dragonballapiandroid.ui.update

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.dragonballapiandroid.data.model.Character
import com.example.dragonballapiandroid.data.repository.CharacterRepository
import com.example.dragonballapiandroid.ui.detail.DetailUiState
import com.example.dragonballapiandroid.ui.detail.toDetailUiState
import com.example.dragonballapiandroid.ui.navegation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

    @HiltViewModel
    class CharacterUpdateViewModel@Inject constructor(
        savedStateHandle: SavedStateHandle,
        private val characterRepository : CharacterRepository
    ): ViewModel() {
        var characterId = 0L
        private val _uiState : MutableStateFlow<DetailUiState> =
            MutableStateFlow(DetailUiState())
        val uiState : StateFlow<DetailUiState>
            get()= _uiState.asStateFlow()
        init {
            viewModelScope.launch {
                val route = savedStateHandle.toRoute<Route.Detail>()
                 characterId = route.id
                val character = characterRepository.readOne(characterId)
                character?.let{
                    _uiState.value = character.getOrNull()!!.toDetailUiState()
                }

            }
        }
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        }
        fun create(){
            viewModelScope.launch {
                val character = Character(
                    id = characterId,
                    name = name,
                    ki = ki,
                    maxKi = maxKi,
                    race = race,
                    gender = gender,
                    description = description,
                    image = _uiState.value.image,
                    affiliation = affiliation
                )
                characterRepository.insert(character)
            }
        }

        var isError =false
        var ki by mutableStateOf(_uiState.value.ki)
        var maxKi by mutableStateOf(_uiState.value.maxKi)
        var name by mutableStateOf(_uiState.value.name)
        var description by mutableStateOf(_uiState.value.description)
        var race by mutableStateOf(_uiState.value.race)
        var affiliation by mutableStateOf(_uiState.value.affiliation)
        var gender by mutableStateOf(_uiState.value.gender)
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
