package com.example.tabbedmoviemanager.Repository


import androidx.room.TypeConverter

class MovieTypeConverters {

    @TypeConverter
    fun fromListOfGenreIdsToString(genreIds:List<Int>) : String {
        return genreIds.joinToString(",")
    }

    @TypeConverter
    fun fromStringToGenreIdsList(s:String) :List<Int> {
        val genreIds = mutableListOf<Int>()
        val list = s.split(',')
        list.forEach { genreIds.add(it.toInt()) }
        return genreIds
    }
}