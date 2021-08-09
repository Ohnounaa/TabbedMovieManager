package com.example.tabbedmoviemanager.Repository

import android.app.Application

class MovieRepositoryInitializer: Application() {
    override fun onCreate() {
        super.onCreate()
        MovieRepository.initialize(this)
    }
}