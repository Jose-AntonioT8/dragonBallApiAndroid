package com.example.dragonballapiandroid.ui.list

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.dragonballapiandroid.data.repository.CharacterRepository
import com.example.dragonballapiandroid.ui.detail.DetailUiState
import com.example.dragonballapiandroid.ui.navegation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.dragonballapiandroid.data.model.Character
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.SelectInstance
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
): ViewModel() {
    private val _uiState : MutableStateFlow<ListUiState> =
        MutableStateFlow( ListUiState.Initial)
    val uiState : StateFlow<ListUiState>
        get()= _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            _uiState.value = ListUiState.Loading
            val allCharacters = characterRepository.readAll()
            val respuestaCorrecta = ListUiState.Succes(
                allCharacters.getOrNull()!!.asListUiState()
            )
            _uiState.value = respuestaCorrecta
        }
    }

    var busquedaParametros by mutableStateOf("")
        private set

     private fun acceptSearch():Boolean {
         if(busquedaParametros.isNotBlank()) {
                 if  (busquedaParametros.matches(Regex("^[1-6]$"))) {
                     return true
                 }
         }
             return false
     }
    fun onBusquedaChanged(nuevoTexto: String) {
        busquedaParametros = nuevoTexto
    }
     fun search(){
        if(acceptSearch()){

            viewModelScope.launch {
                _uiState.value = ListUiState.Loading
                try {
                    val pagina = busquedaParametros.toInt()
                    val paginatedCharacters = characterRepository.readPage(pagina)
                    val respuestaCorrecta = ListUiState.Succes(
                        paginatedCharacters.asListUiState()
                    )
                    _uiState.value = respuestaCorrecta
                } catch (e: Exception) {
                    _uiState.value = ListUiState.Error("Error al cargar la página: ${e.message}")
                }
            }
        } else {
            _uiState.value = ListUiState.Error("Introduce un número de página válido (1-6).")        }
     }
}


sealed class ListUiState{
    object Initial: ListUiState()
    object Loading : ListUiState()
    data class Error(val message :String): ListUiState()

    data class Succes (
        val characters : List<ListItemUiState>
    ): ListUiState()
}
data class ListItemUiState(
    val id:Long,
    val name:String,
    val image: String
)
fun Character.asListItemUiState(): ListItemUiState{
    return ListItemUiState(
        id = this.id,
        name = this.name,
        image = this.image
    )
}
fun List<Character>.asListUiState():List<ListItemUiState>
=this.map(Character::asListItemUiState)