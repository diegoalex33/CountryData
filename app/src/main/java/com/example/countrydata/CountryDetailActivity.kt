
package com.example.countrydata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.countrydata.databinding.ActivityCountryDetailBinding
import com.squareup.picasso.Picasso

class CountryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCountryDetailBinding


    companion object {
        val EXTRA_COUNTRY = "The country"
        var favoriteList = arrayListOf<Country>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val countryInfo = intent.getParcelableExtra<Country>(EXTRA_COUNTRY)
        val countryId = countryInfo?.alpha2Code?.toLowerCase()
        val countryFlagUrl = "https://flagcdn.com/w320/" +countryId+ ".png"

        countryInfo?.isFavorite = false
        binding.textViewCountryDetailName.text = countryInfo?.name
        if (countryInfo?.capital == null){
            binding.textViewCountryDetailCapital.text = "N/a"
        }
        binding.textViewCountryDetailCapital.text = countryInfo?.capital
        binding.textViewCountryDetailRegion.text = countryInfo?.subregion
        binding.textViewCountryDetailArea.text = countryInfo?.area.toString()
        if(countryId!=null){
            Picasso.get().load(countryFlagUrl).into(binding.imageViewCountryDetailFlag)
        }
        binding.textViewCountryDetailPopulation.text = countryInfo?.population.toString()
        binding.buttonCountryDetailFavorite.setOnClickListener {
            countryInfo?.isFavorite = true
            Toast.makeText(this,countryInfo?.name +" has been added to favorites", Toast.LENGTH_SHORT).show()
            if (countryInfo != null) {
                favoriteList.add(countryInfo)
            }
        }

        binding.buttonCountryDetailRandom.setOnClickListener {
            val countryInfo = OpeningActivity.countries[((Math.random())*OpeningActivity.countries.size).toInt()]
            val countryId = countryInfo?.alpha2Code?.toLowerCase()
            val countryFlagUrl = "https://flagcdn.com/w320/" +countryId+ ".png"

            countryInfo?.isFavorite = false
            binding.textViewCountryDetailName.text = countryInfo?.name
            if (countryInfo?.capital == null){
                binding.textViewCountryDetailCapital.text = "N/a"
            }
            binding.textViewCountryDetailCapital.text = countryInfo?.capital
            binding.textViewCountryDetailRegion.text = countryInfo?.subregion
            binding.textViewCountryDetailArea.text = countryInfo?.area.toString()
            if(countryId!=null){
                Picasso.get().load(countryFlagUrl).into(binding.imageViewCountryDetailFlag)
            }
            binding.textViewCountryDetailPopulation.text = countryInfo?.population.toString()
            binding.buttonCountryDetailFavorite.setOnClickListener {
                countryInfo?.isFavorite = true
                Toast.makeText(this,countryInfo?.name +" has been added to favorites", Toast.LENGTH_SHORT).show()
                if (countryInfo != null) {
                    favoriteList.add(countryInfo)
                }
            }
        }

    }
}