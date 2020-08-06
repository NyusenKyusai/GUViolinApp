package com.example.guviolinapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_questions.*

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    //Initialization of variables that will be used
    private lateinit var myDb: DBHelper
    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<QuestionsTable>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers: Int = 0
    private var mQuizLevel: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        //Receiving the extra information from the Intent
        mQuizLevel = intent.getIntExtra("LevelInformation", 1)

        //Initializing the DBHelper variable with this as it's context
        myDb = DBHelper(this)
        //Retrieving the array list of questions from the database using the quiz level that we got from the intent extra from the main ui
        mQuestionList = myDb.getLevelQuestions(mQuizLevel!!)
        //Calling the setQuestion function to set the first question into the activity
        setQuestion()
        //Giving every button the onclicklistener event as well as the context. How the listener event behaves is handled in a later function
        tvOptionOne.setOnClickListener(this)
        tvOptionTwo.setOnClickListener(this)
        tvOptionThree.setOnClickListener(this)
        tvOptionFour.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    //Function that sets the question, options, and picture for the necessary question
    private fun setQuestion() {
        //variable that holds the attributes for a specific question number starting with the index value 0
        val question = mQuestionList!![mCurrentPosition - 1]

        //Function that makes all of the text views back to how they look before they are clicked on
        defaultOptionsView()

        //if statement that determines whether the submit button should say submit or finish
        //depending on whether the current position is the same as the size of the question
        //array list, indicating that we are on the last question
        if(mCurrentPosition == mQuestionList!!.size){
            btnSubmit.text = "FINISH"
        } else {
            btnSubmit.text = "SUBMIT"
        }

        //setting the progress of the progress bar equal to the current question.
        progressBar.progress = mCurrentPosition
        //Setting the text version of progress bar to the current question as well as the
        //maximum of the progress bar. As every level has 15 questions, the max will always be 15
        tvProgress.text = "$mCurrentPosition" + "/" + progressBar.max
        //These values set the text values of each of the text views as each of the options saved to the database.
        tvQuestion.text = question.quiz_questions
        //This variable allows me to create a modular means of calling different png files from
        //the drawables file that depend on the current position as well as the quiz level
        var id = resources.getIdentifier("l${mQuizLevel}q${mCurrentPosition}", "drawable", this.packageName)
        //Sets the image to the variable id we created
        ivImage.setImageResource(id)
        tvOptionOne.text = question.quiz_option_one
        tvOptionTwo.text = question.quiz_option_two
        tvOptionThree.text = question.quiz_option_three
        tvOptionFour.text = question.quiz_option_four
    }

    //Function to return the text views to the default text views
    private fun defaultOptionsView() {
        //Creating a variable to hold the ArrayList of text views
        val options = ArrayList<TextView>()
        //Adding the text views to the array lists with their indexes
        options.add(0, tvOptionOne)
        options.add(1, tvOptionTwo)
        options.add(2, tvOptionThree)
        options.add(3, tvOptionFour)

        //For loop to iterate through the array list for the option
        for (option in options) {
            //Setting the text colour to the usual color for the options
            option.setTextColor(Color.parseColor("#999999"))
            //Setting the typeface to default
            option.typeface = Typeface.DEFAULT
            //Setting the background to be the default background
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    //Function that handles how the text views behave when clicked
    override fun onClick(p0: View?) {
        //When function that deals with the view id individually separating the functions to when the views are being clicked/tapped
        when(p0?.id){
            //When the TextView is clicked with the id tvOptionOne
            R.id.tvOptionOne ->{
                //Calls the selectedOptionView question and feeds it the TextView as well as which option number it is
                selectedOptionView(tvOptionOne, 1)
            }
            //When the TextView is clicked with the id tvOptionTwo
            R.id.tvOptionTwo ->{
                //Calls the selectedOptionView question and feeds it the TextView as well as which option number it is
                selectedOptionView(tvOptionTwo, 2)
            }
            //When the TextView is clicked with the id tvOptionThree
            R.id.tvOptionThree ->{
                //Calls the selectedOptionView question and feeds it the TextView as well as which option number it is
                selectedOptionView(tvOptionThree, 3)
            }
            //When the TextView is clicked with the id tvOptionFour
            R.id.tvOptionFour ->{
                //Calls the selectedOptionView question and feeds it the TextView as well as which option number it is
                selectedOptionView(tvOptionFour, 4)
            }
            //When the Button is clicked
            R.id.btnSubmit ->{
                //When no option is clicked
                if(mSelectedOptionPosition == 0){
                    //The current position is incremented
                    mCurrentPosition++
                    //When statement for determining what will happen is current position is equal to or lower than the array size
                    when{
                        mCurrentPosition <= mQuestionList!!.size ->{
                            //The new question is set
                            setQuestion()
                        //Otherwise we are sent to the results screen
                        } else ->{
                            //Intent is made taking us to the next activity
                            val intent = Intent(this, ResultActivity::class.java)
                            //Putting extra values into the intent that will be needed in the result page
                            intent.putExtra("Correct Answers", mCorrectAnswers)
                            intent.putExtra("Total Question", mQuestionList!!.size)
                            intent.putExtra("Quiz Level", mQuizLevel)
                            //Starting the new activity
                            startActivity(intent)
                            //Closing this activity
                            finish()
                        }
                    }
                } else {
                    //Variable that chooses the question from the question array list depending on the current position. -1 because the index starts at 0 not 1
                    val question = mQuestionList?.get(mCurrentPosition - 1)
                    //if state that checks whether the chosen option is not the correct one
                    if (question!!.quiz_answer != mSelectedOptionPosition){
                        //changes the selection option to red to show it is wrong, by calling the answer view function. It inputs the selected position as well as the xml file we created
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        //If the position is correct, the correct answers counter is incremented
                        mCorrectAnswers++
                    }
                    //Regardless of the user's answer is correct, we set the correct answer to the correct option xml using the answer view
                    answerView(question.quiz_answer, R.drawable.correct_option_border_bg)
                    //If statement that that determines what the button says after the answer is submitted
                    if(mCurrentPosition == mQuestionList!!.size){
                        //When the current position is equal to the size array list, the text is set to finish
                        btnSubmit.text = "FINISH"
                    } else {
                        //When the current position is not equal, the text is set to go to next question
                        btnSubmit.text = "GO TO NEXT QUESTION"
                    }
                    //Set the selected option position to 0, resetting everything
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    //Function that takes in the answer given as well as the drawable xml file
    private fun answerView(answer: Int, drawableView: Int){
        //When function that takes in the answer given as different cases
        when(answer){
            //Case that sets the background to the xml file given to the function, whether it's correct answer or incorrect answer xml file
            1 ->{
                tvOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 ->{
                tvOptionTwo.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 ->{
                tvOptionThree.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 ->{
                tvOptionFour.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

    //Function that takes in a text view and takes the selection option num
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        //Calls the default options view function to reset all the option
        defaultOptionsView()
        //Makes the selected option position equal to the selected option number
        mSelectedOptionPosition = selectedOptionNum

        //sets the text color to a specific colour
        tv.setTextColor(Color.parseColor("#323039"))
        //Makes the text bold
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        //Changes the background of the text view to the selection option xml
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }
}