package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_final.*

class Final : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)

        start.setOnClickListener()
        {
            val intent = Intent(this,Quiz::class.java)
            startActivity(intent)
        }


        exit.setOnClickListener()
        {
            finishAffinity()
        }


    }
}
