package com.example.assignment3

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.file.Files


class MainActivity : AppCompatActivity() {
    companion object{val REQUEST_IMAGE_CAPTURE = 1 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val db = Room.databaseBuilder(applicationContext, EnteriesDB::class.java, "Entery").build()
        val dc = Room.databaseBuilder(applicationContext,ImageDb::class.java,"Image").build()
        var spinner: Spinner = findViewById(R.id.spinner1)

        capture.setOnClickListener {

            dispatchTakePictureIntent()
        }


        add.setOnClickListener()
        {
            Thread {
                var obj1 = Enteries()
                obj1.name = editText.text.toString()
                db.safe().insert(obj1)
                runOnUiThread()
                {
                    Toast.makeText(this, "Inserted Successfully", Toast.LENGTH_SHORT).show()
                    editText.text.clear()
                }
            }.start()
        }

        show.setOnClickListener()
        {
            Thread {
                var lip = db.safe().read()


                var i: Int = 0
                var x = (lip.size) - 1
                var lips = ArrayList<String>()

                while (i <= x) {

                    lips.add(lip[i].name)
                    i++
                }

                runOnUiThread()
                {
                    var aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, lips)
                    spinner.adapter = aa
                }


            }.start()


        }

        remove.setOnClickListener()
        {
            var toDelete : String  = spinner.selectedItem.toString()
            Thread{
                db.safe().remove(toDelete)
            }.start()
        }

        save.setOnClickListener()
        {
            var image = Image()
            //image.data =
        }
    }

    private fun dispatchTakePictureIntent(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{
                takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also{
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
        
    }



}



