package com.codelectro.mvvmnews.db

import androidx.room.TypeConverter
import com.codelectro.mvvmnews.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source) = source.name

    @TypeConverter
    fun toSource(name: String) =
        Source(name, name)

}