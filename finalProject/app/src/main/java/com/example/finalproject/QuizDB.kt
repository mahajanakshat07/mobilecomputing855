package com.example.finalproject

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [(QuizEntity::class)],version = 1)
abstract class QuizDB : RoomDatabase() {

    private lateinit var INSTANCE : QuizDB

    abstract fun print() :QuizDao

    fun getInstance(context: Context): QuizDB {
        synchronized(this) {
            if (INSTANCE == null) {
                INSTANCE = createInstance(context)
            }
            return INSTANCE!!
        }
    }

    private fun createInstance(context: Context) =
        Room.databaseBuilder(context.applicationContext, QuizDB::class.java, "Quiiz")
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Thread(Runnable { getInstance(context).print().insert(prepopulateDb())}).start()
                }
            })
            .build()

    private fun prepopulateDb() : QuizEntity {
        val obj1 = QuizEntity()
        obj1.ques = "Cr7 means ? "
        obj1.a = "a"
        obj1.b = "b"
        obj1.c = "c"
        obj1.d = "d"
        obj1.e = "a"
        return obj1

    }


}