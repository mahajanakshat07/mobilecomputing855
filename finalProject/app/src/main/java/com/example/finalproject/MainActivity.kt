package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = Room.databaseBuilder(applicationContext,LoginDB::class.java,"Information").build() //Build our Login database.

        signup.setOnClickListener()
        {
            var login = Login()                   //Create an object of Login class
            //Setting the fields of created object with entered name and passowrd
            login.uname = entername.text.toString()
            login.upwd = enterpassword.text.toString()
            //Thread Started to run our database transactions and to prevent our application from crashing
            Thread{
                db.safe().save(login)  //save function called to save our object in our table
                runOnUiThread()        //main thread called to access UI elements like Toast to prevent app from crashing
                {
                    Toast.makeText(this,"Sign Up Complete",Toast.LENGTH_SHORT).show()
                    entername.text.clear()
                    enterpassword.text.clear()
                }
            }.start()
        }

        login.setOnClickListener()
        {
            var enterName = entername.text.toString()
            var password : String = ""
            var count : Int = 0
            Thread{
                count = db.safe().count(entername.text.toString())     //To get the count of username in our database & since name is a primary key it should always be 1
                password = db.safe().match(entername.text.toString())  //To get the password according to the username entered
                //test cases for login Button
                if(count==1 && password.equals(enterpassword.text.toString()))
                {
                    runOnUiThread()
                    {
                        Toast.makeText(this,"Welcome " + enterName ,Toast.LENGTH_LONG).show()
                        //If user is suceesful during login,then to tramnsfer the user to another a
                        val intent = Intent(this,QuestionsActivity::class.java)
                        startActivity(intent)
                    }
                }

                else if(count==1 && !(password.equals(enterpassword.text.toString())))
                {
                    runOnUiThread()
                    {
                        Toast.makeText(this,"Wrong password Entered",Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    runOnUiThread()
                    {
                        Toast.makeText(this,"Please SignUp First",Toast.LENGTH_SHORT).show()
                    }
                }
            }.start()
        }


    }
}
