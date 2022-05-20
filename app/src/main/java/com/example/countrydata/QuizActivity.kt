package com.example.countrydata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.countrydata.databinding.ActivityCountriesListBinding
import com.example.countrydata.databinding.ActivityQuizBinding
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.security.acl.Group

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
    lateinit var mcButtons : androidx.constraintlayout.widget.Group
    lateinit var abutton1 : Button
    lateinit var abutton2: Button
    lateinit var abutton3: Button
    lateinit var abutton4: Button
    lateinit var submit: Button
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
        mcButtons = binding.quizGroupMcButtons
        abutton1 = binding.button
        abutton2 = binding.button2
        abutton3 = binding.button3
        abutton4 = binding.button4
        submit = binding.buttonQuizSubmit
        answer = "a"

        nextQuestion()



        submit.setOnClickListener {
            answer = response.text.toString()
            checkAnswer()
            // response.toString() gives you the version of the whole Edittext, not the text itself. you have to do .text.toString()
            nextQuestion()
        }

        abutton1.setOnClickListener {
            if(abutton1.text == curQuestion.country.name){
                score++
            }
            scoreDisplay.text = "Score: " + score + "/" + (numberOfQuestion)
            nextQuestion()
        }

        abutton2.setOnClickListener {
            if(abutton2.text == curQuestion.country.name){
                score++

            }
            scoreDisplay.text = "Score: " + score + "/" + (numberOfQuestion)
            nextQuestion()
        }

        abutton3.setOnClickListener {
            if(abutton3.text == curQuestion.country.name){
                score++

            }
            scoreDisplay.text = "Score: " + score + "/" + (numberOfQuestion)
            nextQuestion()
        }

        abutton4.setOnClickListener {
            if(abutton4.text == curQuestion.country.name){
                score++

            }
            scoreDisplay.text = "Score: " + score + "/" + (numberOfQuestion)
            nextQuestion()
        }

    }

    fun nextQuestion(){
        response.visibility = View.VISIBLE
        submit.visibility = View.VISIBLE
        mcButtons.visibility = View.GONE
        response.setText("")
        // setting the text in an edittext you have to use the function .setText()
        var countryNumber = ((Math.random())*OpeningActivity.countries.size).toInt()
        var questionNumber = ((Math.random())*4).toInt()
        curQuestion = Question(countryNumber, questionNumber)
        numberOfQuestion++
        Picasso.get().load(stockImage).into(binding.flag)
        number.text = "Question " + numberOfQuestion
        determineQuestion()
        makeMc(questionNumber)
    }

    private fun makeMc(isMc : Int) {
       if(isMc%2==0){
           response.visibility = View.GONE
           submit.visibility = View.GONE
           mcButtons.visibility = View.VISIBLE
           abutton1.text = OpeningActivity.countries[((Math.random())*OpeningActivity.countries.size).toInt()].name
           abutton2.text = OpeningActivity.countries[((Math.random())*OpeningActivity.countries.size).toInt()].name
           abutton3.text = OpeningActivity.countries[((Math.random())*OpeningActivity.countries.size).toInt()].name
           abutton4.text = OpeningActivity.countries[((Math.random())*OpeningActivity.countries.size).toInt()].name
           if (isMc==0){
               abutton1.text = curQuestion.country.name
           }
           else if (isMc==1){
               abutton2.text = curQuestion.country.name
           }
           else if (isMc==2){
               abutton3.text = curQuestion.country.name
           }
           else{
               abutton4.text = curQuestion.country.name
           }
       }
    }

    fun determineQuestion(){
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

    fun checkAnswer() {
        if (answer.toLowerCase().equals(curQuestion.country.name.toLowerCase())) {
            score++
        }
        scoreDisplay.text = "Score: " + score + "/" + (numberOfQuestion)
    }


}