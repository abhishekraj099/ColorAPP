package com.example.myadmi.data.ColorEntity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colors")
data class ColorEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "time") val time: Long
)