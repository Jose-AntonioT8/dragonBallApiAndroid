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
    class CharacterUpdateViewModel@Inject constructor(
        savedStateHandle: SavedStateHandle,
        private val characterRepository : CharacterRepository
    ): ViewModel() {
        var isError =false
        var characterId = 0L
        var ki by mutableStateOf("")
        var maxKi by mutableStateOf("")
        var name by mutableStateOf("")
        var description by mutableStateOf("")
        var race by mutableStateOf("")
        var affiliation by mutableStateOf("")
        var gender by mutableStateOf("")
        private var characterImage: String = ""
        private val _uiState : MutableStateFlow<DetailUiState> =
            MutableStateFlow(DetailUiState())
        val uiState : StateFlow<DetailUiState> = _uiState.asStateFlow()
        init {
            viewModelScope.launch {
                val route = savedStateHandle.toRoute<Route.Detail>()
                 characterId = route.id
                val character = characterRepository.readOne(characterId)
                character.let{character ->
                    name = character.getOrNull()!!.name
                    ki = character.getOrNull()!!.ki
                    maxKi = character.getOrNull()!!.maxKi
                    race = character.getOrNull()!!.race
                    gender = character.getOrNull()!!.gender
                    description = character.getOrNull()!!.description
                    affiliation = character.getOrNull()!!.affiliation

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
                val character = Character(
                    id = characterId,
                    name = name,
                    ki = ki,
                    maxKi = maxKi,
                    race = race,
                    gender = gender,
                    description = description,
                    image = characterImage,
                    affiliation = affiliation
                )
                characterRepository.insert(character)
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
