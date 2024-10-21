package com.example.myadmi



import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.google.firebase.Firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.random.Random


import java.util.UUID

@HiltViewModel
class ColorViewModel @Inject constructor(
    private val database: AppDatabase,
    private val firestore: FirebaseFirestore
) : ViewModel() {
    private val colorDao = database.colorDao()
    private val colorsRef = firestore.collection("colors")

    val colors: Flow<List<ColorEntry>> = colorDao.getAllColors().map { entities ->
        entities.map { ColorEntry(it.color, it.time, it.id) }
    }

    private val _pendingSync = MutableStateFlow(0)
    val pendingSync = _pendingSync.asStateFlow()

    fun addRandomColor() {
        val randomColor = generateRandomColor()
        addColor(randomColor)
    }

    private fun generateRandomColor(): String {
        val random = Random.Default
        return String.format("#%06X", random.nextInt(0xFFFFFF + 1))
    }

    fun addColor(color: String) {
        viewModelScope.launch {
            val newColor = ColorEntry(
                color = color,
                time = System.currentTimeMillis(),
                id = generateId()
            )
            colorDao.insertColor(ColorEntity(newColor.id, newColor.color, newColor.time))
            uploadColorToFirestore(newColor) { success ->
                if (!success) {
                    _pendingSync.value++
                }
            }
        }
    }

    private fun uploadColorToFirestore(colorEntry: ColorEntry, onUploadComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                colorsRef.document(colorEntry.id).set(colorEntry).await()
                onUploadComplete(true)
            } catch (e: Exception) {
                e.printStackTrace()
                onUploadComplete(false)
            }
        }
    }

    fun syncColors() {
        viewModelScope.launch {
            try {
                val localColors = colors.first()
                localColors.forEach { color ->
                    uploadColorToFirestore(color) { success ->
                        if (success) {
                            _pendingSync.value--
                        }
                    }
                }
                val snapshot = colorsRef.get().await()
                val firebaseColors = snapshot.documents.mapNotNull { it.toObject(ColorEntry::class.java) }
                firebaseColors.forEach { firebaseColor ->
                    val existingColor = colorDao.findById(firebaseColor.id)
                    if (existingColor == null) {
                        colorDao.insertColor(ColorEntity(firebaseColor.id, firebaseColor.color, firebaseColor.time))
                    }
                }
                _pendingSync.value = 0
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteAllColors() {
        viewModelScope.launch {
            colorDao.deleteAllColors()
        }
    }

    private fun generateId(): String {
        return UUID.randomUUID().toString()
    }
}