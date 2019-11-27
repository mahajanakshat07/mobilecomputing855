package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_lose.*

class Lose : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lose)

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
