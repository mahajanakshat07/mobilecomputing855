package com.example.android.navigation

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(Login::class)],version = 1)   //Specify the android complier that this will be a database with the name of the entitiy provided and version of database which we are using
abstract class LoginDB : RoomDatabase() {           //abstarct class that extends RoomDatabase()
    abstract fun safe(): LoginDAO
    //abstarct function with 0 parameteres and DAO class as its return type.

}