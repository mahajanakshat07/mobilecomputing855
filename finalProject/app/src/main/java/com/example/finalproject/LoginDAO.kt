package com.example.finalproject

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LoginDAO {
    @Insert                     //Query to insert the values provided by user in our table
    fun save(login: Login)     //fun to access the Insert query

    @Query("Select Password from Information where Name = :enter")  //Query to fetch a password from table respective to entered username
    fun match(enter : String) : String

    @Query("Select Count(*) from Information where Name = :name")   //Query to count the number of usernames with respect to enter name
    fun count(name : String) : Int
}