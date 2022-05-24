package com.example.countrydata

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Question(countryNumber: Int, questionNumber: Int) {

    var countryList = OpeningActivity.countries
    var questionTypes = listOf("capital", "population", "area", "flag", "region")
    var questionType = questionTypes[questionNumber]
    var country = countryList[countryNumber]
}