package com.example.guviolinapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main_u_i.*

class MainUI : AppCompatActivity() {

    //Initialization of variables that will be used
    lateinit var myDb: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_u_i)

        //Setting the database variable and it's context
        myDb = DBHelper(this)

        //OnClickListener Events for each button to redirect to the next activity
        btnLevel1.setOnClickListener{
            val intent = Intent(this, QuizQuestionsActivity::class.java)
            //Creating a message to determine what questions will appear in the next activity
            intent.putExtra("LevelInformation", 1)
            startActivity(intent)
            finish()
        }

        btnLevel2.setOnClickListener {
            val intent = Intent(this, QuizQuestionsActivity::class.java)
            intent.putExtra("LevelInformation", 2)
            startActivity(intent)
            finish()
        }

        btnLevel3.setOnClickListener {
            val intent = Intent(this, QuizQuestionsActivity::class.java)
            intent.putExtra("LevelInformation", 3)
            startActivity(intent)
            finish()
        }

        btnGame1.setOnClickListener{
            val intent = Intent(this, QuizGameActivity::class.java)
            intent.putExtra("GameInformation", 1)
            startActivity(intent)
            finish()
        }
    }
}