package com.example.myadmi

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "colors")
data class ColorEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "time") val time: Long
)

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


@Database(entities = [ColorEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun colorDao(): ColorDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "color_database"
                ).build().also { instance = it }
            }
        }
    }
}