package com.example.dragonballapiandroid.data.remote.model

data class CharacterListRemote(
    val items:List<CharacterListItemRemote>
)

data class CharacterListItemRemote(

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