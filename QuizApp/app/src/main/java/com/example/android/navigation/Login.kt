package com.example.android.navigation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Information")  //Create a table with name Information
class Login {

    @PrimaryKey()                    //This will tell the complier that this column will be a primary key
    @ColumnInfo(name = "Name")       //This is used to describe the name of the column
    var uname : String = ""         //Initialized the value of column with an empty String

    @ColumnInfo(name = "Password")
    var upwd : String = ""
}