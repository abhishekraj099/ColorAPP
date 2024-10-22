package com.example.myadmi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ColorViewModel @Inject constructor(
    private val database: AppDatabase,
    private val firebaseDatabase: FirebaseDatabase  // Injected Realtime Database
) : ViewModel() {

    private val colorDao = database.colorDao()
    private val colorsRef = firebaseDatabase.getReference("colors")

    val colors: Flow<List<ColorEntry>> = colorDao.getAllColors().map { entities ->
        entities.map { ColorEntry(it.id, it.color, it.time) }
    }

    // Flow to observe Firebase Realtime Database changes
    val firebaseColors: Flow<List<ColorEntry>> = callbackFlow {
        val listener = colorsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val colorList = mutableListOf<ColorEntry>()
                for (childSnapshot in snapshot.children) {
                    childSnapshot.getValue(ColorEntry::class.java)?.let {
                        colorList.add(it)
                    }
                }
                trySend(colorList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error: ${error.message}")
            }
        })

        awaitClose {
            colorsRef.removeEventListener(listener)
        }
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

    private fun addColor(color: String) {
        viewModelScope.launch {
            try {
                val id = UUID.randomUUID().toString()
                val newColor = ColorEntry(
                    id = id,
                    color = color,
                    time = System.currentTimeMillis()
                )

                // Log the new color entry
                Log.d("ColorViewModel", "Generated ColorEntry: $newColor")

                // Save to local database
                colorDao.insertColor(ColorEntity(newColor.id, newColor.color, newColor.time))

                // Save to Firebase Realtime Database
                colorsRef.child(id).setValue(newColor)
                    .addOnSuccessListener {
                        Log.d("Firebase", "Color added successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firebase", "Error adding color", e)
                        _pendingSync.value++  // Sync flag if failure occurs
                    }

            } catch (e: Exception) {
                e.printStackTrace()
                _pendingSync.value++
            }
        }
    }


    fun syncColors() {
        viewModelScope.launch {
            try {
                // Upload local colors to Firebase
                val localColors = colors.first()
                localColors.forEach { color ->
                    try {
                        colorsRef.child(color.id).setValue(color)
                            .await()
                        _pendingSync.value = (_pendingSync.value - 1).coerceAtLeast(0)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                // Fetch colors from Firebase
                val snapshot = colorsRef.get().await()
                val firebaseColors = mutableListOf<ColorEntry>()

                snapshot.children.forEach { childSnapshot ->
                    childSnapshot.getValue(ColorEntry::class.java)?.let {
                        firebaseColors.add(it)
                    }
                }

                // Update local database
                firebaseColors.forEach { firebaseColor ->
                    colorDao.insertColor(
                        ColorEntity(firebaseColor.id, firebaseColor.color, firebaseColor.time)
                    )
                }
                _pendingSync.value = 0
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}