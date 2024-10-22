package com.example.myadmi

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.initialize
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@HiltAndroidApp
class YourApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
    }
}

