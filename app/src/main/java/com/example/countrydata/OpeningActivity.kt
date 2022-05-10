package com.example.countrydata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.countrydata.databinding.ActivityOpeningBinding

class OpeningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOpeningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpeningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val quizButton = binding.buttonOpeningQuiz
        val learnButton = binding.buttonOpeningLearn
        val learningActivityIntent = Intent(this, LearningActivity::class.java)
        val quizActivityIntent = Intent(this, QuizActivity::class.java)


        learnButton.setOnClickListener {
            startActivity(learningActivityIntent)
        }
        quizButton.setOnClickListener {
            startActivity(quizActivityIntent)
        }

    }
}