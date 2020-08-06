package com.example.guviolinapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Calls up the method to check to see whether this was the first installation of the app
        checkFirstTime()

        //Listener event to register the clicking of the submit button and move to the next activity
        btnStart.setOnClickListener{
            //If statement that determines whether something was submitted into the Edit Text
            if(etName.text.toString().isEmpty()) {
                //Toast to prompt the user/ input validation
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            } else {
                //Function to save the name
                saveUsername(etName.text.toString())
                //creating an intent to start the next activity and then close this activity to conserve memory
                val intent = Intent(this, MainUI::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    //function to check whether it is the first installation
    private fun checkFirstTime() {
        //function that takes the Shared Preferences location
        val sharedPrefs = getSharedPreferences("FIRST_INSTALL", Context.MODE_PRIVATE)
        //Editor for shared preferences
        var editor = sharedPrefs.edit()

        //checks to see whether there is a firstInstall file, if is isn't the default value is returned
        var firstInstall = sharedPrefs.getString("firstInstall", "")

        //if it is the first install, the value will be "" and not false
        if(firstInstall == "False"){
            //if the value is false, it hasn't been the first opening of the app, so the user is redirected to the ui activity
            val intent = Intent(this, MainUI::class.java)
            startActivity(intent)
            finish()
        } else {
            //if it is the first install, the sharedPrefs is changed to reflect that, and the user can continue to work in this activity
            editor.putString("firstInstall", "False")
            editor.apply()
        }
    }

    //This function saves the name the user inputs into shared preferences
    private fun saveUsername(username: String) {
        //Functions that takes the shared preferences location
        val sharedPrefs = getSharedPreferences("SAVE_USERNAME", Context.MODE_PRIVATE)
        //Editor for shared preferences
        var editor = sharedPrefs.edit()
        //Puts the username into the shared preferences location using the key Username as well as applying the change
        editor.putString("Username", username)
        editor.apply()
    }
}