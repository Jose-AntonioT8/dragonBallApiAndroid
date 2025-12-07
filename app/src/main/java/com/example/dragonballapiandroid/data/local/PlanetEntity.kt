package com.example.dragonballapiandroid.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dragonballapiandroid.data.model.Planet


@Entity("planet")
data class PlanetEntity(
    @PrimaryKey
    val id:Long,
    val name:String,
    val isDestroyed:Boolean,
    val description:String,
    val image:String,
)

fun Planet.toEntity(): PlanetEntity{
    return PlanetEntity(
        id = this.id,
        name = this.name,
        isDestroyed = this.isDestroyed,
        description = this.description,
        image = this.image,
    )
}

fun PlanetEntity.toModel(): Planet{
    return Planet(
        id = this.id,
        name = this.name,
        isDestroyed = this.isDestroyed,
        description = this.description,
        image = this.image,
    )
}

fun List<PlanetEntity>.toModel(): List<Planet>{
    return this.map(PlanetEntity::toModel)
}