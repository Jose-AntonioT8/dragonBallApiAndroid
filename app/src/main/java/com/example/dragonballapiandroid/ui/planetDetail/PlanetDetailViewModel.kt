package com.example.dragonballapiandroid.ui.planetDetail


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.dragonballapiandroid.data.repository.PlanetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import androidx.navigation.toRoute
import com.example.dragonballapiandroid.ui.navegation.Route
import androidx.lifecycle.viewModelScope
import com.example.dragonballapiandroid.data.model.Planet
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class DetailUiState(
    val id : Long = 0,
    val name : String = "",
    val image : String = "",
    val description : String = "",
    val isDestroyed: Boolean = false
)


@HiltViewModel
class PlanetDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val planetRepository : PlanetRepository
): ViewModel() {
    private val _uiState : MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState())
    val uiState : StateFlow<DetailUiState>
        get()= _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            val route = savedStateHandle.toRoute<Route.PlanetDetail>()
            val planetId = route.id
            val planet = planetRepository.readOne(planetId)
            planet.let{
                _uiState.value = planet.getOrNull()!!.toDetailUiState()
            }

        }
    }
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
    }
    fun delete(id:Long){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            planetRepository.delete(id)
        }
    }

}

fun Planet.toDetailUiState(): DetailUiState = DetailUiState(
    name = this.name,
    id = this.id,
    image = this.image,
    isDestroyed = this.isDestroyed,
    description = this.description,
)
