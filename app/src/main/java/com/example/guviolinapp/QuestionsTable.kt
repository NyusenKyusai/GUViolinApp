package com.example.guviolinapp

import java.sql.Blob

data class QuestionsTable(var quiz_level: Int, var question_number: Int, var quiz_questions: String, var quiz_image: Blob, var quiz_option_one: String, var quiz_option_two: String, var quiz_option_three: String, var quiz_option_four: String, var quiz_answer: Int)