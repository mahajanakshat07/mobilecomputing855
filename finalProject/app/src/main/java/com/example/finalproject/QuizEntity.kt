package com.example.finalproject

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Quiiz")
class QuizEntity {

    @PrimaryKey()
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