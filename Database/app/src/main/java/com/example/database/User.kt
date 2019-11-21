package com.example.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "akshat")
class User {

    @PrimaryKey()
    @ColumnInfo(name = "Name")
    var uname : String = ""

    @ColumnInfo(name = "Password")
    var upwd : String = ""
}