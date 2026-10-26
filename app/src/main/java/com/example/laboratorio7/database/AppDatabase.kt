package com.example.laboratorio7.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        CharacterEntity::class,
        LocationEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    // DAO de personajes
    abstract fun characterDao(): CharacterDao

    // DAO de ubicaciones
    abstract fun locationDao(): LocationDao
}
