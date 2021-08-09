package com.example.tabbedmoviemanager.Repository


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tabbedmoviemanager.Models.Movie

@Database(entities = [Movie:: class],
    version = 1,
    exportSchema = false)
@TypeConverters(MovieTypeConverters::class)

    abstract class MovieDatabase: RoomDatabase() {
       abstract fun movieDao() : MovieDAO
        companion object {
            //we want database to be a singleton
            @Volatile
            private var INSTANCE: MovieDatabase? = null
            fun getInstance(context: Context): MovieDatabase? {
                synchronized(this) {
                    var instance = INSTANCE
                    if(instance == null) {
                        instance = Room.databaseBuilder(context.applicationContext,
                            MovieDatabase::class.java,
                            "movie_database")
                            .fallbackToDestructiveMigration().build()
                        INSTANCE = instance
                    }
                    return instance
                }
            }
        }
    }

