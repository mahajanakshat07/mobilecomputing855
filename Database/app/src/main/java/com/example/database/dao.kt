package com.example.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface dao {

    @Insert
    fun save(user: User)

    @Query("Select Password from akshat where Name = :abc")
    fun match(abc:String) : String

    @Query("Select * from akshat")
    fun read()  : List<User>




}