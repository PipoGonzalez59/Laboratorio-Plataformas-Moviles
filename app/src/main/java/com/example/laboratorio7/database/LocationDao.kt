package com.example.laboratorio7.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    // Obtener todas las ubicaciones
    @Query("SELECT * FROM locations")
    fun getAllLocations(): Flow<List<LocationEntity>>

    // Obtener una ubicacion por su ID
    @Query("SELECT * FROM locations WHERE id = :id")
    fun getLocationById(id: Int): Flow<LocationEntity?>

    // Guardar las ubicaciones
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocations(locations: List<LocationEntity>)

    // Contar las ubicaciones guardadas
    @Query("SELECT COUNT(*) FROM locations")
    suspend fun getLocationsCount(): Int

    // Eliminar todas las ubicaciones
    @Query("DELETE FROM locations")
    suspend fun deleteAllLocations()
}
