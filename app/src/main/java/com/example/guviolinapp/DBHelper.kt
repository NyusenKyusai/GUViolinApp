package com.example.guviolinapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    private lateinit var db: SQLiteDatabase

    companion object{
        //Companion object that holds the name of everything in the database
        private val DATABASE_NAME = "violininfo.db"
        private val GAME_TABLE = "game_table"
        private val COL_GAME_NUMBER = "game_number"
        private val COL_GAME_SCORE = "game_score"
        private val SCORE_TABLE = "score_table"
        private val COL_SCORE_LEVEL = "quiz_level"
        private val COL_QUIZ_SCORE = "quiz_score"
        private val QUESTION_TABLE = "questions_table"
        private val COL_QUIZ_LEVEL = "quiz_level"
        private val COL_QUESTION_NUM = "question_number"
        private val COL_QUIZ_QUESTION = "quiz_questions"
        private val COL_QUIZ_OPTION_ONE = "quiz_options_one"
        private val COL_QUIZ_OPTION_TWO = "quiz_options_two"
        private val COL_QUIZ_OPTION_THREE = "quiz_options_three"
        private val COL_QUIZ_OPTION_FOUR = "quiz_options_four"
        private val COL_QUIZ_ANSWER = "quiz_answer"
    }

    //On create of the Application creating the actual database in the device
    override fun onCreate(db: SQLiteDatabase) {
        this.db = db

        //Variable that holds the SQL query to create the table
        val CREATE_GAME_TABLE =
                "CREATE TABLE ${GAME_TABLE}" +
                "($COL_GAME_NUMBER INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_GAME_SCORE INTEGER" +
                ")"
        //Variable that holds the SQL query to create the table
        val CREATE_QUESTION_TABLE =
                "CREATE TABLE ${QUESTION_TABLE}" +
                "($COL_QUIZ_LEVEL INTEGER, " +
                "$COL_QUESTION_NUM INTEGER, " +
                "$COL_QUIZ_QUESTION TEXT, " +
                "$COL_QUIZ_OPTION_ONE TEXT, " +
                "$COL_QUIZ_OPTION_TWO TEXT, " +
                "$COL_QUIZ_OPTION_THREE TEXT, " +
                "$COL_QUIZ_OPTION_FOUR TEXT, " +
                "$COL_QUIZ_ANSWER INTEGER, " +
                "PRIMARY KEY ($COL_QUIZ_LEVEL, $COL_QUESTION_NUM)" +
                ")"
        //Variable that holds the SQL query to create the table
        val CREATE_SCORE_TABLE =
                "CREATE TABLE ${SCORE_TABLE}" +
                "($COL_SCORE_LEVEL INTEGER PRIMARY KEY, " +
                "$COL_QUIZ_SCORE INTEGER, " +
                "FOREIGN KEY ($COL_SCORE_LEVEL) REFERENCES $QUESTION_TABLE($COL_QUIZ_LEVEL) ON DELETE CASCADE" +
                ")"

        //Functions that execute the sql script. The first one prevents null in the db
        db.execSQL(CREATE_QUESTION_TABLE)
        db.execSQL(CREATE_GAME_TABLE)
        db.execSQL(CREATE_SCORE_TABLE)
        fillQuestionsTable()
    }

    //Function that drops the database and remakes it for when the database is updated. It can be done two ways.
    //The app can be deleted, or the version can be increased
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_SCORE_TABLE = "DROP TABLE IF EXISTS $SCORE_TABLE"
        val DROP_GAME_TABLE = "DROP TABLE IF EXISTS $GAME_TABLE"
        val DROP_QUESTION_TABLE = "DROP TABLE IF EXISTS $QUESTION_TABLE"

        db.execSQL(DROP_SCORE_TABLE)
        db.execSQL(DROP_GAME_TABLE)
        db.execSQL(DROP_QUESTION_TABLE)
        onCreate(db)
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }


    //Function to insert values into the Score table
    fun insertScoreTable(quizLevel: Int, quizScore: Int): Boolean? {
        //Function to insert values that will be inserted into the database
        val cv = ContentValues()
        //Inserting the values with a specific identifier in which the value needs to be inserted
        cv.put(COL_SCORE_LEVEL, quizLevel)
        cv.put(COL_QUIZ_SCORE, quizScore)
        //Executing the actual insert database
        val res = db.insert(SCORE_TABLE, null, cv)
        return !res.equals(-1)
    }

    //Function to insert values into the Game table
    fun insertGameTable(gameNum: Int, gameScore: Int): Boolean? {
        //Function to insert values that will be inserted into the database
        val cv = ContentValues()
        //Inserting the values with a specific identifier in which the value needs to be inserted
        cv.put(COL_GAME_NUMBER, gameNum)
        cv.put(COL_GAME_SCORE, gameScore)
        //Executing the actual insert database
        val res = db.insert(GAME_TABLE, null, cv)
        return !res.equals(-1)
    }

    //Function to insert values into the Questions table
    private fun insertQuestionsTable(questionTable: QuestionsTable): Boolean? {
        //Function to insert values that will be inserted into the database
        val cv = ContentValues()
        //Inserting the values with a specific identifier in which the value needs to be inserted
        cv.put(COL_QUIZ_LEVEL, questionTable.quiz_level)
        cv.put(COL_QUESTION_NUM, questionTable.question_number)
        cv.put(COL_QUIZ_QUESTION, questionTable.quiz_questions)
        cv.put(COL_QUIZ_OPTION_ONE, questionTable.quiz_option_one)
        cv.put(COL_QUIZ_OPTION_TWO, questionTable.quiz_option_two)
        cv.put(COL_QUIZ_OPTION_THREE, questionTable.quiz_option_three)
        cv.put(COL_QUIZ_OPTION_FOUR, questionTable.quiz_option_four)
        cv.put(COL_QUIZ_ANSWER, questionTable.quiz_answer)
        //Executing the actual insert database
        val res = db.insert(QUESTION_TABLE, null, cv)
        return !res.equals(-1)
    }

    //Function to insert all the questions into the database
    private fun fillQuestionsTable() {
        //Variable with the data class holding the values for each question
        val l1q1 = QuestionsTable(1, 1, "What note is this?", "Minim",
            "Semibreve", "Crochet", "Quaver", 4)
        //Calling the insertQuestionsTable to insert the question
        insertQuestionsTable(l1q1)

        val l1q2 = QuestionsTable(1, 2, "What note is this?", "Quaver",
            "Minim", "Crochet", "Semibreve", 3)
        insertQuestionsTable(l1q2)

        val l1q3 = QuestionsTable(1, 3, "What rest is this?", "Whole",
            "Eighth", "Half", "Quarter", 2)
        insertQuestionsTable(l1q3)

        val l1q4 = QuestionsTable(1, 4, "What rest is this?", "Quarter",
            "Eighth", "Whole", "Half", 1)
        insertQuestionsTable(l1q4)

        val l1q5 = QuestionsTable(1, 5, "What rest is this?", "Whole",
        "Eighth", "Quarter", "Half", 1)
        insertQuestionsTable(l1q5)

        val l1q6 = QuestionsTable(1, 6, "What rest is this?", "Quarter",
            "Eighth", "Whole", "Half", 4)
        insertQuestionsTable(l1q6)

        val l1q7 = QuestionsTable(1, 7, "What note is this?", "Minim",
            "Quaver", "Semibreve", "Crochet", 1)
        insertQuestionsTable(l1q7)

        val l1q8 = QuestionsTable(1, 8, "What note is this?", "Quaver",
            "Semibreve", "Crochet", "Minim", 2)
        insertQuestionsTable(l1q8)

        val l1q9 = QuestionsTable(1, 9, "How many beats in this rest?", "Half Beat",
            "One Beat", "Two Beats", "Four Beats", 3)
        insertQuestionsTable(l1q9)

        val l1q10 = QuestionsTable(1, 10, "How many beats in this rest?", "Four Beats",
            "One Beat", "Two Beats", "Half Beat", 4)
        insertQuestionsTable(l1q10)

        val l1q11 = QuestionsTable(1, 11, "How many beats in this rest?", "One Beat",
            "Half Beat", "Two Beats", "Four Beats", 4)
        insertQuestionsTable(l1q11)

        val l1q12 = QuestionsTable(1, 12, "How many beats in this note?", "Four Beats",
            "One Beat", "Half Beat", "Two Beats", 2)
        insertQuestionsTable(l1q12)

        val l1q13 = QuestionsTable(1, 13, "How many beats in this rest?", "Two Beats",
            "One Beat", "Four Beats", "Half Beat", 2)
        insertQuestionsTable(l1q13)

        val l1q14 = QuestionsTable(1, 14, "How many beats in this note?", "Two Beats",
            "Half Beat", "Four Beats", "One Beat", 3)
        insertQuestionsTable(l1q14)

        val l1q15 = QuestionsTable(1, 15, "How many beats in this note?", "One Beat",
            "Four Beats", "Half Beat", "Two Beats", 3)
        insertQuestionsTable(l1q15)

        val l2q1 = QuestionsTable(2, 1, "What string is this?", "D",
            "A", "G", "E", 1)
        insertQuestionsTable(l2q1)

        val l2q2 = QuestionsTable(2, 2, "What note is this?", "F sharp",
            "E", "G sharp", "A", 1)
        insertQuestionsTable(l2q2)

        val l2q3 = QuestionsTable(2, 3, "What note is this?", "G",
            "B", "A", "C", 2)
        insertQuestionsTable(l2q3)

        val l2q4 = QuestionsTable(2, 4, "What note is this?", "E",
            "F sharp", "A", "G sharp", 4)
        insertQuestionsTable(l2q4)

        val l2q5 = QuestionsTable(2, 5, "What note is this?", "G",
            "A", "B", "C", 4)
        insertQuestionsTable(l2q5)

        val l2q6 = QuestionsTable(2, 6, "What string is this?", "A",
            "D", "E", "G", 3)
        insertQuestionsTable(l2q6)

        val l2q7 = QuestionsTable(2, 7, "What note is this?", "B",
            "A", "C", "G", 2)
        insertQuestionsTable(l2q7)

        val l2q8 = QuestionsTable(2, 8, "What note is this?", "E",
            "D", "G", "F sharp", 4)
        insertQuestionsTable(l2q8)

        val l2q9 = QuestionsTable(2, 9, "What note is this?", "A",
            "B", "C sharp", "D", 4)
        insertQuestionsTable(l2q9)

        val l2q10 = QuestionsTable(2, 10, "What note is this?", "F sharp",
            "D", "E", "G", 3)
        insertQuestionsTable(l2q10)

        val l2q11 = QuestionsTable(2, 11, "What note is this?", "A",
            "B", "C sharp", "D", 2)
        insertQuestionsTable(l2q11)

        val l2q12 = QuestionsTable(2, 12, "What string is this?", "D",
            "G", "E", "A", 4)
        insertQuestionsTable(l2q12)

        val l2q13 = QuestionsTable(2, 13, "What note is this?", "C sharp",
            "D", "B", "A", 1)
        insertQuestionsTable(l2q13)

        val l2q14 = QuestionsTable(2, 14, "What note is this?", "E",
            "G", "F sharp", "D", 2)
        insertQuestionsTable(l2q14)

        val l2q15 = QuestionsTable(2, 15, "What string is this?", "A",
            "D", "G", "E", 3)
        insertQuestionsTable(l2q15)

        val l3q1 = QuestionsTable(3, 1, "What is this called?", "Treble Clef",
            "Tie", "Stave", "Flat", 3)
        insertQuestionsTable(l3q1)

        val l3q2 = QuestionsTable(3, 2, "What is this called?", "Stave",
            "Bass Clef", "Sharp", "Treble Clef", 3)
        insertQuestionsTable(l3q2)

        val l3q3 = QuestionsTable(3, 3, "What is this called?", "Bar",
            "Stave", "Sharp", "Bass Clef", 1)
        insertQuestionsTable(l3q3)

        val l3q4 = QuestionsTable(3, 4, "What is this called?", "Time Signature",
            "Treble Clef", "Natural", "Bass Clef", 2)
        insertQuestionsTable(l3q4)

        val l3q5 = QuestionsTable(3, 5, "What is this called?", "Bar",
            "Key Signature", "Sharp", "Tie", 2)
        insertQuestionsTable(l3q5)

        val l3q6 = QuestionsTable(3, 6, "What is this called?", "Treble Clef",
            "Key Signature", "Bar", "Tie", 4)
        insertQuestionsTable(l3q6)

        val l3q7 = QuestionsTable(3, 7, "What is this called?", "Flat",
            "Sharp", "Bar", "Natural", 1)
        insertQuestionsTable(l3q7)

        val l3q8 = QuestionsTable(3, 8, "What is this called?", "Treble Clef",
            "Flat", "Sharp", "Bass Clef", 4)
        insertQuestionsTable(l3q8)

        val l3q9 = QuestionsTable(3, 9, "What is this called?", "Sharp",
            "Time Signature", "Flat", "Tie", 2)
        insertQuestionsTable(l3q9)

        val l3q10 = QuestionsTable(3, 10, "What is this called?", "Bass Clef",
            "Bar", "Natural", "Sharp", 3)
        insertQuestionsTable(l3q10)

        val l3q11 = QuestionsTable(3, 11, "What is this called?", "Decrescendo",
            "Accent", "Forte", "Crescendo", 1)
        insertQuestionsTable(l3q11)

        val l3q12 = QuestionsTable(3, 12, "What is this called?", "Decrescendo",
            "Forte", "Accent", "Piano", 4)
        insertQuestionsTable(l3q12)

        val l3q13 = QuestionsTable(3, 13, "What is this called?", "Forte",
            "Accent", "Crescendo", "Decrescendo", 1)
        insertQuestionsTable(l3q13)

        val l3q14 = QuestionsTable(3, 14, "What is this called?", "Decrescendo",
            "Accent", "Crescendo", "Forte", 2)
        insertQuestionsTable(l3q14)

        val l3q15 = QuestionsTable(3, 15, "What is this called?", "Forte",
            "Piano", "Accent", "Crescendo", 4)
        insertQuestionsTable(l3q15)


    }

    //Function get the questions back from the database.
    fun getLevelQuestions(quizLevel: Int): ArrayList<QuestionsTable> {
        //Initializing db
        val db = this.readableDatabase
        //
        val selection = "$COL_QUIZ_LEVEL = ? "
        //
        val selectionArgs = arrayOf(quizLevel.toString())
        //Creating the ArrayList that holds the questions retrieved from the database
        val rv = ArrayList<QuestionsTable>()
        //Query that gets the values from the database
        val csr = db.query(QUESTION_TABLE,null /* ALL columns */,selection,selectionArgs,null,null,null)

        //While loop that insert the values into the array list while there is a next value
        while (csr.moveToNext()) {
            //Adding values to the array list
            rv.add(
                //Inserting the values into the data class
                QuestionsTable(
                    //Getting the actual values from the query
                    csr.getInt(csr.getColumnIndex(COL_QUIZ_LEVEL)),
                    csr.getInt(csr.getColumnIndex(COL_QUESTION_NUM)),
                    csr.getString(csr.getColumnIndex(COL_QUIZ_QUESTION)),
                    csr.getString(csr.getColumnIndex(COL_QUIZ_OPTION_ONE)),
                    csr.getString(csr.getColumnIndex(COL_QUIZ_OPTION_TWO)),
                    csr.getString(csr.getColumnIndex(COL_QUIZ_OPTION_THREE)),
                    csr.getString(csr.getColumnIndex(COL_QUIZ_OPTION_FOUR)),
                    csr.getInt(csr.getColumnIndex(COL_QUIZ_ANSWER))
                )
            )

        }

        //Closing the database query
        csr.close()
        //Returning the ArrayList
        return rv
    }

    //Function get the scores back from the database.
    fun getAllScores(): ArrayList<ScoreTable> {
        val db = this.readableDatabase
        //Creating the ArrayList that holds the questions retrieved from the database
        val rv = ArrayList<ScoreTable>()
        //While loop that insert the values into the array list while there is a next value
        val csr = db.query(SCORE_TABLE,null /* ALL columns */,null,null,null,null,null)

        //While loop that insert the values into the array list while there is a next value
        while (csr.moveToNext()) {
            //Adding values to the array list
            rv.add(
                //Inserting the values into the data class
                ScoreTable(
                    //Getting the actual values from the query
                    csr.getInt(csr.getColumnIndex(COL_SCORE_LEVEL)),
                    csr.getInt(csr.getColumnIndex(COL_QUIZ_SCORE))
                )
            )
        }

        //Closing the database query
        csr.close()
        //Returning the ArrayList
        return rv
    }

    //Function get the Game scores back from the database.
    fun getAllGameScores(): ArrayList<GameTable> {
        val db = this.readableDatabase
        //Creating the ArrayList that holds the questions retrieved from the database
        val rv = ArrayList<GameTable>()
        //While loop that insert the values into the array list while there is a next value
        val csr = db.query(GAME_TABLE,null /* ALL columns */,null,null,null,null,null)

        //While loop that insert the values into the array list while there is a next value
        while (csr.moveToNext()) {
            //Adding values to the array list
            rv.add(
                //Inserting the values into the data class
                GameTable(
                    //Getting the actual values from the query
                    csr.getInt(csr.getColumnIndex(COL_GAME_NUMBER)),
                    csr.getInt(csr.getColumnIndex(COL_GAME_SCORE))
                )
            )
        }

        //Closing the database query
        csr.close()
        //Returning the ArrayList
        return rv
    }
}