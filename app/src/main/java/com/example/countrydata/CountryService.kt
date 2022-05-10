package com.example.countrydata

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryService {
    @GET("all")
    // all the fields are concatenated and separated by commas
    fun getCountryInfo(@Query("fields") fields : String) : Call<List<Country>>

}