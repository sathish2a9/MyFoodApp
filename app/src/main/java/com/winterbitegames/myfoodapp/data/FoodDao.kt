package com.winterbitegames.myfoodapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FoodDao {

    @Query("select * from store")
    fun getAll(): LiveData<List<Store>>


    @Query("update store set count=:count where foodName=:foodName")
    fun update(foodName: String, count: Int): Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(score: Store)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(score: List<Store>)

    @Delete
    fun delete(score: Store)

    @Query("delete from Store")
    fun nukeTable()

}