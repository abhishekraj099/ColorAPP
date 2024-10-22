package com.example.myadmi.data.dataclass


data class ColorEntry(
    val id: String = "",
    val color: String = "",
    val time: Long = System.currentTimeMillis()
)