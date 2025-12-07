package com.example.dragonballapiandroid.ui.planetCreation


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballapiandroid.data.model.Planet
import com.example.dragonballapiandroid.data.repository.PlanetRepository
import com.example.dragonballapiandroid.ui.planetDetail.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetCreationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val planetRepository: PlanetRepository
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
            val planet = Planet(
                id = newId,
                name = name,
                description = description,
                image = "https://dragonball-api.com/characters/vegeta_normal.webp",
                isDestroyed = isDestroyed
            )
            planetRepository.insert(planet)
        }
    }
    private suspend fun getLastId(): Long {
        val characters = planetRepository.observe().first().getOrNull()
        val maxId = characters?.maxOfOrNull { it.id } ?: 0L
        return maxId + 1
    }
    var isError =false
    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var isDestroyed by mutableStateOf(false)
}

