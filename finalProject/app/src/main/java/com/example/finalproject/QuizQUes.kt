package com.example.finalproject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Hello")
class QuizQUes {

    @PrimaryKey()
    @ColumnInfo(name = "number")
    var i : Int = 0

    @ColumnInfo(name = "Questions")
    var ques : String = ""

    @ColumnInfo(name = "optiona")
    var a : String = ""

    @ColumnInfo(name = "optionb")
    var b : String = ""

    @ColumnInfo(name = "optionc")
    var c : String = ""

    @ColumnInfo(name = "optiond")
    var d : String = ""

    @ColumnInfo(name = "correct")
    var e : String = ""
}