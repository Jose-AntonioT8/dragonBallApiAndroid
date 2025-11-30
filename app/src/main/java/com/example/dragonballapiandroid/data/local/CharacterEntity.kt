package com.example.dragonballapiandroid.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dragonballapiandroid.data.model.Character


@Entity("character")
data class CharacterEntity(
    @PrimaryKey
    val id:Long,
    val name:String,
    val ki:String,
    val maxKi:String,
    val race:String,
    val gender:String,
    val description:String,
    val image:String,
    val affiliation:String
)

fun Character.toEntity(): CharacterEntity{
    return CharacterEntity(
        id = this.id,
        name = this.name,
        ki = this.ki,
        maxKi = this.maxKi,
        race = this.race,
        gender = this.gender,
        description = this.description,
        image = this.image,
        affiliation = this.affiliation

    )
}

fun CharacterEntity.toModel(): Character{
    return Character(
        id = this.id,
        name = this.name,
        ki = this.ki,
        maxKi = this.maxKi,
        race = this.race,
        gender = this.gender,
        description = this.description,
        image = this.image,
        affiliation = this.affiliation

    )
}

fun List<CharacterEntity>.toModel(): List<Character>{
    return this.map(CharacterEntity::toModel)
}