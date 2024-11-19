package com.example.meals.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {
    @TypeConverter
    fun fromAnyToString (arr : Any?): String {
        if (arr==null){
            return ""

        }else return arr as String

    }
    @TypeConverter
    fun fromStringToAny (atr : String?):Any{
        if (atr==null){
            return ""

        }else return atr
    }

}