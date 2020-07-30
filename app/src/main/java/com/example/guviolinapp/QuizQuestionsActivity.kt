package com.example.guviolinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class QuizQuestionsActivity : AppCompatActivity() {

    //Initialization of variables that will be used
    lateinit var myDb: DBHelper
    private var mQuizLevel: String? = null

    //Setting the database variable and it's context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        //Receiving the extra information from the Intent
        mQuizLevel = intent.getStringExtra("LevelInformation")

        myDb = DBHelper(this)

        val questionList = myDb.getAllQuestions()

        val tvOptionOne = findViewById<TextView>(R.id.tvOptionOne)

        Log.d("Database Query",questionList.toString())
    }
}