package com.example.myadmi

import java.util.UUID

data class ColorEntry(
    val color: String,
    val time: Long,
    val id: String = UUID.randomUUID().toString()
)
