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

    //Setting the database variable and it's context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        //Receiving the extra information from the Intent
        mQuizLevel = intent.getIntExtra("LevelInformation", 1)

        myDb = DBHelper(this)

        mQuestionList = myDb.getLevelQuestions(mQuizLevel!!)

        setQuestion()

        tvOptionOne.setOnClickListener(this)
        tvOptionTwo.setOnClickListener(this)
        tvOptionThree.setOnClickListener(this)
        tvOptionFour.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    private fun setQuestion() {
        val question = mQuestionList!![mCurrentPosition - 1]

        defaultOptionsView()

        if(mCurrentPosition == mQuestionList!!.size){
            btnSubmit.text = "FINISH"
        } else {
            btnSubmit.text = "SUBMIT"
        }

        progressBar.progress = mCurrentPosition
        tvProgress.text = "$mCurrentPosition" + "/" + progressBar.max

        tvQuestion.text = question.quiz_questions
        var id = resources.getIdentifier("ic_flag_of_argentina", "drawable", this.packageName)
        ivImage.setImageResource(id)
        tvOptionOne.text = question.quiz_option_one
        tvOptionTwo.text = question.quiz_option_two
        tvOptionThree.text = question.quiz_option_three
        tvOptionFour.text = question.quiz_option_four
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0, tvOptionOne)
        options.add(1, tvOptionTwo)
        options.add(2, tvOptionThree)
        options.add(3, tvOptionFour)

        for (option in options) {
            option.setTextColor(Color.parseColor("#999999"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.tvOptionOne ->{
                selectedOptionView(tvOptionOne, 1)
            }
            R.id.tvOptionTwo ->{
                selectedOptionView(tvOptionTwo, 2)
            }
            R.id.tvOptionThree ->{
                selectedOptionView(tvOptionThree, 3)
            }
            R.id.tvOptionFour ->{
                selectedOptionView(tvOptionFour, 4)
            }
            R.id.btnSubmit ->{
                if(mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionList!!.size ->{
                            setQuestion()
                        } else ->{
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra("Correct Answers", mCorrectAnswers)
                            intent.putExtra("Total Question", mQuestionList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionList?.get(mCurrentPosition - 1)
                    if (question!!.quiz_answer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnswers++
                    }

                    answerView(question.quiz_answer, R.drawable.correct_option_border_bg)

                    if(mCurrentPosition == mQuestionList!!.size){
                        btnSubmit.text = "FINISH"
                    } else {
                        btnSubmit.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int){
        when(answer){
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

    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#323039"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }
}