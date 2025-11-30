package com.example.dragonballapiandroid.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("character")
data class CharacterEntity(
    @PrimaryKey
    val id:Long,
    val name:String?,
    val imgUrl:String?
)

fun Character.toEntity(): CharacterEntity{
    return CharacterEntity(
        id = this.id,
        name = this.name,
        imgUrl = this.imgUrl

    )
}

fun CharacterEntity.toModel(): Character{
    return Character(
        id = this.id,
        name = this.name,
        imgUrl = this.imgUrl

    )
}

fun List<CharacterEntity>.toModel(): List<Character>{
    return this.map(CharacterEntity::toModel)
}