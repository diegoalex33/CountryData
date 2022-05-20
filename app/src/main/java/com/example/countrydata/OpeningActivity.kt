package com.example.countrydata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countrydata.databinding.ActivityCountriesListBinding
import com.example.countrydata.databinding.ActivityOpeningBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpeningActivity : AppCompatActivity() {

    val TAG = "OpeningActivity"

    private lateinit var binding: ActivityOpeningBinding

    companion object{
        var countries = arrayListOf<Country>()
    }

    lateinit var countryList: List<Country>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpeningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val quizButton = binding.buttonOpeningQuiz
        val learnButton = binding.buttonOpeningLearn
        val countryListActivityIntent = Intent(this, CountryListActivity::class.java)
        val quizActivityIntent = Intent(this, QuizActivity::class.java)

        Picasso.get().load("https://pbs.twimg.com/media/CCNxWJdUAAEo7Pq?format=png&name=4096x4096").into(binding.imageView)


        val countryApi = RetrofitHelper.getInstance().create(CountryService::class.java)
        val countryCall = countryApi.getCountryInfo("name,capital,area,population,alpha2Code")

        countryCall.enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                countryList = response.body() ?: listOf<Country>()
                countries.addAll(countryList)
                Log.d(TAG, "On response:"+ OpeningActivity.countries.size)
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Log.d(TAG, "onFailure: fail ${t.message}")
            }

        })



        learnButton.setOnClickListener {
            startActivity(countryListActivityIntent)
        }
        quizButton.setOnClickListener {
            startActivity(quizActivityIntent)
        }

    }
}