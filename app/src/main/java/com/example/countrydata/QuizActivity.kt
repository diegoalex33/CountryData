package com.example.countrydata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.countrydata.databinding.ActivityCountriesListBinding
import com.example.countrydata.databinding.ActivityQuizBinding
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class QuizActivity : AppCompatActivity() {

    val TAG = "QuizActivity"
    private lateinit var curQuestion: Question
    private lateinit var binding: ActivityQuizBinding
    var numberOfQuestion = 0
    var score = 0
    lateinit var question: TextView
    lateinit var number : TextView
    lateinit var answer : String
    lateinit var flag : ImageView
    lateinit var response : EditText
    lateinit var scoreDisplay : TextView
    var stockImage = "https://st.depositphotos.com/2363887/2579/i/450/depositphotos_25790143-stock-photo-earth-globe-realistic-3-d.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        question = binding.textViewQuizQuestion
        flag = binding.flag
        number = binding.textViewQuizQuestionNumber
        scoreDisplay = binding.textViewQuizScore
        response = binding.editTextQuizAnswer

        nextQuestion()



        binding.buttonQuizSubmit.setOnClickListener {
            answer = response.text.toString()
            // response.toString() gives you the version of the whole Edittext, not the text itself. you have to do .text.toString()
            checkAnswer()
            nextQuestion()
        }

    }

    fun nextQuestion(){
        response.setText("")
        // setting the text in an edittext you have to use the function .setText()
        var countryNumber = ((Math.random())*OpeningActivity.countries.size).toInt()
        Log.d(TAG, ""+ countryNumber)
        var questionNumber = ((Math.random())*4).toInt()
        curQuestion = Question(countryNumber, questionNumber)
        numberOfQuestion++
        Picasso.get().load(stockImage).into(binding.flag)
        number.text = "Question " + numberOfQuestion
        if(curQuestion.questionType.equals("capital")){
            question.text = curQuestion.country.capital + " is the capital of what country?"
        }
        else if(curQuestion.questionType.equals("population")){
            question.text = curQuestion.country.population.toString() + " is the population of what country?"
        }
        else if(curQuestion.questionType.equals("area")){
            question.text = curQuestion.country.area.toString() + " is the area of what country?"
        }
        else{
            val countryId = curQuestion.country.alpha2Code.toLowerCase()
            val countryFlagUrl = "https://flagcdn.com/w320/" +countryId+ ".png"
            question.text = "To which country does this flag belong"
            Picasso.get().load(countryFlagUrl).into(binding.flag)
        }
    }

    fun checkAnswer(){
        if(answer.toLowerCase().equals(curQuestion.country.name.toLowerCase())){
            score++
        }
        scoreDisplay.text = "Score: " + score + "/" + (numberOfQuestion)
    }


}