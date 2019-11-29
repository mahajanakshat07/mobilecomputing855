package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_quiz.*

class Quiz : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val dc = Room.databaseBuilder(applicationContext, QuizDB::class.java, "Quiiz").build()

       /*Thread{

            var abc1 = QuizEntity()
              abc1.ques = "In which year did Maradona score a goal with his hand?"
              abc1.a = "1980"
              abc1.b = "1986"
              abc1.c = "1988"
              abc1.d = "1983"
              abc1.e = "1986"
           dc.print().insert(abc1)
           var abc2 = QuizEntity()
           abc2.ques = "Which city became capital of West-Germany in 1949?"
           abc2.a = "Munich"
           abc2.b = "Berlin"
           abc2.c = "Bonn"
           abc2.d = "Stuttgart"
           abc2.e = "Bonn"
           dc.print().insert(abc2)
           var abc3 = QuizEntity()
           abc3.ques = "How many stars has the American flag got?"
           abc3.a = "47"
           abc3.b = "52"
           abc3.c = "49"
           abc3.d = "50"
           abc3.e = "50"
           dc.print().insert(abc3)
           var abc4 = QuizEntity()
           abc4.ques = "How many oscars did the Titanic movie got?"
           abc4.a = "12"
           abc4.b = "11"
           abc4.c = "10"
           abc4.d = "09"
           abc4.e = "11"
           dc.print().insert(abc4)
           var abc5 = QuizEntity()
           abc5.ques = "Which country did Spain beat to win davis cup 2019 ?"
           abc5.a = "Canada"
           abc5.b = "Serbia"
           abc5.c = "Belgium"
           abc5.d = "France"
           abc5.e = "Canada"
           dc.print().insert(abc5)
           var abc6 = QuizEntity()
           abc6.ques = "In Australian football, what is the maximum number of players allowed on the field at a time?"
           abc6.a = "45"
           abc6.b = "40"
           abc6.c = "36"
           abc6.d = "24"
           abc6.e = "36"
           var abc7 = QuizEntity()
           abc7.ques = "Who does Phoebe marry in FRIENDS ?"
           abc7.a = "Ross"
           abc7.b = "Joey"
           abc7.c = "Mike"
           abc7.d = "Chandler"
           abc7.e = "Mike"
           dc.print().insert(abc7)
           var abc8 = QuizEntity()
           abc8.ques = "What player was the first to win five straight Wimbledon tennis titles?"
           abc8.a = "Andre Aggasi"
           abc8.b = "Bjorn Borg"
           abc8.c = "Roger Federer"
           abc8.d = "Arthur Ashe"
           abc8.e = "Bjorn Borg"
           dc.print().insert(abc8)
           var abc9 = QuizEntity()
           abc9.ques = "How many Rings are on Olympoic logo"
           abc9.a = "6"
           abc9.b = "3"
           abc9.c = "7"
           abc9.d = "5"
           abc9.e = "5"
           dc.print().insert(abc9)
           var abc10 = QuizEntity()
           abc10.ques = "Who won 2019 UEFA Champions League?"
           abc10.a = "Barcelona"
           abc10.b = "Real Madrid"
           abc10.c = "Liverpool"
           abc10.d = "Tottenham"
           abc10.e = "Liverpool"
           dc.print().insert(abc10)
           runOnUiThread()
           {
               Toast.makeText(this, "Questions saved", Toast.LENGTH_SHORT).show()
           }
       }.start()


        Thread{
            var lip : Array<QuizEntity> = dc.print().read()
            runOnUiThread()
            {
                textView6.text = lip.size.toString()
            }

        }.start()


*/



Thread {
   var lip: Array<QuizEntity> = dc.print().read()
   var i = 0
   var prize = 0
   while (i < (lip.size) - 1) {
       runOnUiThread()
       {
           ques.text = lip[i].ques
           a.text = lip[i].a
           b.text = lip[i].b
           c.text = lip[i].c
           d.text = lip[i].d
       }

       var answer = ""
       Help1.setOnClickListener()
       {
           if (a.text.equals(lip[i].e))
           {

               b.visibility = View.GONE
               c.visibility = View.GONE
           }
           else if (b.text.equals(lip[i].e))
           {
               c.visibility = View.GONE
               d.visibility = View.GONE
           }
           else if (c.text.equals(lip[i].e))
           {
               a.visibility = View.GONE
               d.visibility = View.GONE
           }
           else
           {
               c.visibility = View.GONE
               a.visibility = View.GONE
           }
           runOnUiThread()
           {
               Toast.makeText(this, "You have taken 50-50 helpline", Toast.LENGTH_SHORT).show()
           }
           Help1.visibility = View.GONE
       }


       submit.setOnClickListener()
       {
           var selected: Int = well.checkedRadioButtonId
           when (selected) {
               R.id.a -> runOnUiThread()
               {
                   answer = a.text.toString()
               }
               R.id.b -> runOnUiThread()
               {
                   answer = b.text.toString()
               }
               R.id.c -> runOnUiThread()
               {
                   answer = c.text.toString()
               }
               R.id.d -> runOnUiThread()
               {
                   answer = d.text.toString()
               }
           }

           if (answer.equals(lip[i].e))
           {
               i++
               if(i==lip.size)
               {
                       runOnUiThread()
                       {
                           val intent = Intent(this,Final::class.java)
                           startActivity(intent)
                       }
               }
               runOnUiThread()
               {
                   Toast.makeText(this, "You answered correctly", Toast.LENGTH_SHORT).show()
                   textView10.visibility = (View.VISIBLE)
                   var money = i*100
                   prize += money

                   textView10.text = prize.toString()

               }



           }
           else {
               runOnUiThread()
               {
                   val intent = Intent(this,Lose::class.java)
                   startActivity(intent)
                   Toast.makeText(this, "You have selected wrong option", Toast.LENGTH_SHORT).show()
               }
           }

           if(Help1.visibility==View.GONE)
           {
               runOnUiThread()
               {
                   a.visibility = View.VISIBLE
                   b.visibility = View.VISIBLE
                   c.visibility = View.VISIBLE
                   d.visibility = View.VISIBLE
               }

           }


       }






   }


}.start()


}
}
