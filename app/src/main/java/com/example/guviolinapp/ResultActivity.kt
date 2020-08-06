package com.example.guviolinapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    ////Initialization of variables that will be used
    private lateinit var myDB: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        //function that takes the Shared Preferences location
        val sharedPrefs = getSharedPreferences("SAVE_USERNAME", Context.MODE_PRIVATE)
        //checks to see whether there is a username key in the sharedPrefs file, if is isn't the default value is returned
        var username = sharedPrefs.getString("Username", "")
        //Setting the name text view to the username variable
        tvName.text = "$username"

        //Receiving the extra information from the Intent
        val totalQuestions = intent.getIntExtra("Total Question", 0)
        val correctAnswers = intent.getIntExtra("Correct Answers", 0)
        val quizLevel = intent.getIntExtra("Quiz Level", 0)

        //Setting the score text view to have the correct answers out of the total questions
        tvScore.text = "Your Score is $correctAnswers out of $totalQuestions"

        //Setting the database variable and it's context
        myDB = DBHelper(this)

        //Calling the insert function for the score table
        myDB.insertScoreTable(quizLevel, correctAnswers)

        //On click listener even for btnFinish
        btnFinish.setOnClickListener{
            //Starting the new activity
            //Intent is made taking us to the next activity
            startActivity(Intent(this, MainUI::class.java))
            //Closing this activity
            finish()
        }
    }
}