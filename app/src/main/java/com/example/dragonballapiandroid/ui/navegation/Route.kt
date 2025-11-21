package com.example.dragonballapiandroid.ui.navegation


import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


import com.example.dragonballapiandroid.ui.detail.CharacterDetailScreen
import com.example.dragonballapiandroid.ui.list.CharacterListScreen
import kotlinx.serialization.Serializable
import kotlin.Unit

@Serializable
sealed class Route(val route:String) {
    @Serializable
    data object List:Route("character_list")
    @Serializable
    data class Detail(val id:Long):Route(route = "character_detail[$id]")
}

fun NavController.navigateToCharacterDetail(id:Long) {
    this.navigate(Route.Detail(id))
}

fun NavController.navigateToList(){
    this.navigate(Route.List)
}

fun NavGraphBuilder.characterDetailDestination(
    modifier:Modifier = Modifier,
    onNavegationBack:()->Unit,

    ) {
    composable<Route.Detail> {


            backStackEntry ->
        CharacterDetailScreen(
            modifier = modifier,
            onNavegationBack={
                    onNavegationBack()
            }
        )


    }
}

fun NavGraphBuilder.characterListDestination(
    modifier:Modifier = Modifier,
    onNavigateToDetails:(Long)->Unit
) {
    composable<Route.List> {

        CharacterListScreen(
            modifier = modifier,
            onShowDetail = { id ->
                onNavigateToDetails(id)
            }
        )


    }
}