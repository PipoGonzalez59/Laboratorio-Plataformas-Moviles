package com.example.laboratorio7.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    // Obtener todos los personajes
    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    // Obtener un personaje por su ID
    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacterById(id: Int): Flow<CharacterEntity?>

    // Obtener solo los personajes favoritos
    @Query("SELECT * FROM characters WHERE isFavorite = 1")
    fun getFavoriteCharacters(): Flow<List<CharacterEntity>>

    // Guardar una lista de personajes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    // Cambiar el estado de favorito
    @Query("UPDATE characters SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    // Contar los personajes guardados
    @Query("SELECT COUNT(*) FROM characters")
    suspend fun getCharactersCount(): Int

    // Eliminar todos los personajes
    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacters()
}
