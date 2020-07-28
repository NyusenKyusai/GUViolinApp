package com.example.guviolinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class QuizQuestionsActivity : AppCompatActivity() {

    private var mQuizLevel: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mQuizLevel = intent.getStringExtra("LevelInformation")
    }
}