package com.example.assignment3

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Entery")
class Enteries {
    @PrimaryKey()
    @ColumnInfo(name = "Name")
    var name : String = ""
}