package com.example.myadmi.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myadmi.data.ColorEntity.ColorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {
    @Query("SELECT * FROM colors")
    fun getAllColors(): Flow<List<ColorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColor(color: ColorEntity)

    @Query("DELETE FROM colors")
    suspend fun deleteAllColors()

    @Query("SELECT * FROM colors WHERE id = :id LIMIT 1")
    suspend fun findById(id: String): ColorEntity?
}