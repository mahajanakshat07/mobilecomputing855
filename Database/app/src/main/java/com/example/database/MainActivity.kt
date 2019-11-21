package com.example.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(applicationContext,AppDB::class.java,"akshat").build()


        button.setOnClickListener()
        {
           var user1 = User()
            user1.uname = editText.text.toString()
            user1.upwd = editText2.text.toString()
            Thread{
                db.safe().save(user1)
                runOnUiThread()
                {
                    Toast.makeText(this,"Signup Complete",Toast.LENGTH_SHORT).show()
                    editText.text.clear()
                    editText2.text.clear()

                }
            }.start()

        }

        Thread{

            db.safe().read().forEach()
            {
                Log.i("@AKSHAT","${it.uname} and ${it.upwd} ")
            }
        }.start()


        button2.setOnClickListener(){
            var password : String = ""
            var name : String = editText.text.toString()
            Thread{

                password = db.safe().match(name)
                if(password.equals(editText2.text.toString())){
                    runOnUiThread()
                    {
                        Toast.makeText(this,"Login Complete",Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    runOnUiThread()
                    {
                        Toast.makeText(this,"Please Signup First",Toast.LENGTH_SHORT).show()
                    }

                }
            }.start()

        }
    }
}
