package com.example.checkandbutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        val  iol   = intent.getStringExtra("value")
        val xyz : TextView =  findViewById(R.id.newanswer)
        xyz.text = iol

    }
}
