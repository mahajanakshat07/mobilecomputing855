package com.example.assignment3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EnteriesDao {
    @Insert
    fun insert(enteries: Enteries)

    @Query("Select * from Entery")
    fun read() : Array<Enteries>

    @Query("DELETE from Entery where Name = :name")
    fun remove(name:String)
}