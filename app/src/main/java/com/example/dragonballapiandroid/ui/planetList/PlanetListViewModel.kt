package com.example.dragonballapiandroid.ui.planetList


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballapiandroid.data.repository.PlanetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.dragonballapiandroid.data.model.Planet
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetListViewModel @Inject constructor(
    private val planetRepository: PlanetRepository
): ViewModel() {
    private val _uiState : MutableStateFlow<ListUiState> =
        MutableStateFlow( ListUiState.Initial)
    val uiState : StateFlow<ListUiState>
        get()= _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            _uiState.value = ListUiState.Loading
            planetRepository.observe().collect { result ->
                if (result.isSuccess) {
                    val planet = result.getOrNull()!!
                    if (planet.isNotEmpty()){
                        val uiPlanets = planet.asListUiState()
                        _uiState.value = ListUiState.Succes(uiPlanets)
                    }else {
                        planetRepository.refresh()
                        planetRepository.observe().collect { result ->
                            val planet = result.getOrNull()!!
                            if (planet.isNotEmpty()) {
                                val uiPlanets = planet.asListUiState()
                                _uiState.value = ListUiState.Succes(uiPlanets)
                            }
                        }
                    }

                } else {
                    _uiState.value = ListUiState.Error("No se han cargado los planetas")
                }
            }
        }
    }

    var busquedaParametros by mutableStateOf("")
        private set

    private fun acceptSearch():Boolean {
        if(busquedaParametros.isNotBlank()) {
            if  (busquedaParametros.matches(Regex("^[1-2]$"))) {
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
                    val paginatedPlanets = planetRepository.readPage(pagina)
                    val respuestaCorrecta = ListUiState.Succes(
                        paginatedPlanets.asListUiState()
                    )
                    _uiState.value = respuestaCorrecta
                } catch (e: Exception) {
                    _uiState.value = ListUiState.Error("Error al cargar la página: ${e.message}")
                }
            }
        } else {
            _uiState.value = ListUiState.Error("Introduce un número de página válido (1-2).")        }
    }
}


sealed class ListUiState{
    object Initial: ListUiState()
    object Loading : ListUiState()
    data class Error(val message :String): ListUiState()

    data class Succes (
        val planets : List<ListItemUiState>
    ): ListUiState()
}
data class ListItemUiState(
    val id:Long,
    val name:String,
    val image: String
)
fun Planet.asListItemUiState(): ListItemUiState{
    return ListItemUiState(
        id = this.id,
        name = this.name,
        image = this.image
    )
}
fun List<Planet>.asListUiState():List<ListItemUiState>
        =this.map(Planet::asListItemUiState)