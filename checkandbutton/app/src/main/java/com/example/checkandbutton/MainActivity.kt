package com.example.checkandbutton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mgoogle : CheckBox = findViewById(R.id.google)
        var myahoo : CheckBox = findViewById(R.id.yahoo)
        var mbing : CheckBox = findViewById(R.id.Bingo)
        var mbutton : Button = findViewById(R.id.submit)
        val mtext : TextView = findViewById(R.id.answer)
        var abc : String = " "


        mgoogle.setOnClickListener()
        {
            if(mgoogle.isChecked()){
                abc = mgoogle.text.toString()
                val intent = Intent(this,DisplayActivity::class.java)
                startActivity(intent)
            }

            else
                abc = "Hello"

        }

        mbutton.setOnClickListener()
        {
            mtext.text=abc
            val intent = Intent(this,DisplayActivity::class.java)
            intent.putExtra("value",abc)
            startActivity(intent)
        }
    }
}
