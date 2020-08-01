package com.example.guviolinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class QuizQuestionsActivity : AppCompatActivity() {

    //Initialization of variables that will be used
    lateinit var myDb: DBHelper
    private var mQuizLevel: Int? = null

    //Setting the database variable and it's context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        //Receiving the extra information from the Intent
        mQuizLevel = intent.getIntExtra("LevelInformation", 1)

        myDb = DBHelper(this)

        val questionList = myDb.getLevelQuestions(mQuizLevel!!)

        //val tvOptionOne = findViewById<TextView>(R.id.tvOptionOne)

        Log.w("Database Query",questionList.toString())


    }
}