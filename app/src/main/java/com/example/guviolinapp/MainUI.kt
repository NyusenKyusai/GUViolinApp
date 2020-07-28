package com.example.guviolinapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main_u_i.*

class MainUI : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_u_i)

        btnLevel1.setOnClickListener{
            val intent = Intent(this, QuizQuestionsActivity::class.java)
            intent.putExtra("LevelInformation", "Level1")
            startActivity(intent)
            finish()
        }

        btnLevel2.setOnClickListener {
            val intent = Intent(this, QuizQuestionsActivity::class.java)
            intent.putExtra("LevelInformation", "Level2")
            startActivity(intent)
            finish()
        }

        btnLevel3.setOnClickListener {
            val intent = Intent(this, QuizQuestionsActivity::class.java)
            intent.putExtra("LevelInformation", "Level3")
            startActivity(intent)
            finish()
        }

        btnGame1.setOnClickListener{
            val intent = Intent(this, QuizGameActivity::class.java)
            intent.putExtra("GameInformation", "Game1")
            startActivity(intent)
            finish()
        }
    }
}