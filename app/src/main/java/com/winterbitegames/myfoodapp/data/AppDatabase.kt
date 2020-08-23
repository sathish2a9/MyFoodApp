package com.winterbitegames.myfoodapp.data


import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Store::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartDao(): FoodDao

}
