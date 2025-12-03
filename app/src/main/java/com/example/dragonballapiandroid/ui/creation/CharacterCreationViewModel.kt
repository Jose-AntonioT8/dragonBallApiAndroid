package com.example.dragonballapiandroid.ui.creation

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
import com.example.dragonballapiandroid.ui.list.ListUiState
import com.example.dragonballapiandroid.ui.list.asListUiState
import com.example.dragonballapiandroid.ui.navegation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull

@HiltViewModel
class CharacterCreationViewModel@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val characterRepository : CharacterRepository
): ViewModel() {
    private val _uiState : MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState())
    val uiState : StateFlow<DetailUiState>
        get()= _uiState.asStateFlow()
    init {

    }
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
    }
    fun create(){
        viewModelScope.launch {
            val newId = getLastId()
            val character = Character(
                id = newId,
                name = name,
                ki = ki,
                maxKi = maxKi,
                race = race,
                gender = gender,
                description = description,
                image = "https://dragonball-api.com/characters/vegeta_normal.webp",
                affiliation = affiliation
            )
            characterRepository.insert(character)
        }
    }
private suspend fun getLastId(): Long {
    val characters = characterRepository.observe().first().getOrNull()
    val maxId = characters?.maxOfOrNull { it.id } ?: 0L
    return maxId + 1
}
    var isError =false
    var ki by mutableStateOf("")
    var maxKi by mutableStateOf("")
    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var race by mutableStateOf("")
    var affiliation by mutableStateOf("")
    var gender by mutableStateOf("")
}

