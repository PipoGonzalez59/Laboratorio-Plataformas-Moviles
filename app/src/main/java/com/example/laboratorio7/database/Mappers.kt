package com.example.laboratorio7.database

import com.example.laboratorio7.Character
import com.example.laboratorio7.Location

// Convertir Character a CharacterEntity
fun Character.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        species = species,
        status = status,
        gender = gender,
        image = image,
        isFavorite = isFavorite
    )
}

// Convertir CharacterEntity a Character
fun CharacterEntity.toModel(): Character {
    return Character(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image,
        isFavorite = isFavorite
    )
}

// Convertir Location a LocationEntity
fun Location.toEntity(): LocationEntity {
    return LocationEntity(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}

// Convertir LocationEntity a Location
fun LocationEntity.toModel(): Location {
    return Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}
