package com.example.dragonballapiandroid.data.remote.model

data class CharacterListRemote(
    val results:List<CharacterListItemRemote>
)

data class CharacterListItemRemote(

    val name: String,
    val url: String,
)
data class CharacterRemote(
    val id: Long,
    val name: String,
    val ki:String,
    val maxKi:String,
    val race:String,
    val gender:String,
    val description:String,
    val image:String,
    val affiliation:String
)