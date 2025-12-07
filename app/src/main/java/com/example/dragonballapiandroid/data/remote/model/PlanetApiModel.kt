package com.example.dragonballapiandroid.data.remote.model

data class PlanetListRemote(
    val items:List<PlanetListItemRemote>
)

data class PlanetListItemRemote(

    val id: Long,
    val name: String,
    val description:String,
    val image:String,
    val isDestroyed: Boolean
)
data class PlanetRemote(
    val id: Long,
    val name: String,
    val description:String,
    val image:String,
    val isDestroyed: Boolean
)