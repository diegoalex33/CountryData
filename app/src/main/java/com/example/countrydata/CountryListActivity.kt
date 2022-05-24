package com.example.countrydata

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countrydata.databinding.ActivityCountriesListBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class CountryListActivity : AppCompatActivity() {

    val TAG = "CountryListActivity"

    var curList = 1

    companion object{
        var favChecked = false
    }
    lateinit var countryList: List<Country>
    private lateinit var binding: ActivityCountriesListBinding
    private lateinit var adapter: CountryAdapter
    lateinit var favSwitch: Switch


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountriesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val countryApi = RetrofitHelper.getInstance().create(CountryService::class.java)
        val countryCall = countryApi.getCountryInfo("name,capital,area,population,subregion,alpha2Code")

        countryCall.enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                countryList = response.body() ?: listOf<Country>()
                Log.d(TAG, "On response:")
                adapter = CountryAdapter(countryList)
                binding.recyclerViewCountryList.adapter = adapter
                binding.recyclerViewCountryList.layoutManager =
                    LinearLayoutManager(this@CountryListActivity)
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Log.d(TAG, "onFailure: fail ${t.message}")
            }

        })

        favSwitch = binding.switchToggleFavorite





        favSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            //toast for when checked
            if (isChecked) {
                Toast.makeText(this, "Showing favorites", Toast.LENGTH_SHORT).show()
                adapter = CountryAdapter(CountryDetailActivity.favoriteList)
                binding.recyclerViewCountryList.adapter = adapter
                binding.recyclerViewCountryList.layoutManager =
                    LinearLayoutManager(this@CountryListActivity)
                binding.switchToggleFavorite.text = "Show all"
                curList *= -1
                favChecked = true
            }
            //toast for when unchecked
            if (!isChecked) {
                Toast.makeText(this, "Showing all", Toast.LENGTH_SHORT).show()
                adapter = CountryAdapter(countryList)
                binding.recyclerViewCountryList.adapter = adapter
                binding.recyclerViewCountryList.layoutManager =
                    LinearLayoutManager(this@CountryListActivity)
                binding.switchToggleFavorite.text = "Show favorites"
                curList *= -1
                favChecked = false
            }
        }

        binding.buttonListRandom.setOnClickListener {
            if(favSwitch.isChecked){
                val country = CountryDetailActivity.favoriteList[((Math.random())*CountryDetailActivity.favoriteList.size).toInt()]
                val countryDetailIntent = Intent(this, CountryDetailActivity::class.java).apply {
                    putExtra(CountryDetailActivity.EXTRA_COUNTRY, country)
                }
                startActivity(countryDetailIntent)
            }
            else{
                val country = countryList[((Math.random())*countryList.size).toInt()]
                val countryDetailIntent = Intent(this, CountryDetailActivity::class.java).apply {
                    putExtra(CountryDetailActivity.EXTRA_COUNTRY, country)
                }
                startActivity(countryDetailIntent)
            }
        }
    }


        override fun onCreateOptionsMenu(menu: Menu): Boolean {
            // Inflate the options menu from XML
            val inflater = menuInflater
            inflater.inflate(R.menu.sorting_menu, menu)

            // Get the SearchView and set the searchable configuration
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            (menu.findItem(R.id.searchBar).actionView as SearchView).apply {
                // Assumes current activity is the searchable activity
                setSearchableInfo(searchManager.getSearchableInfo(componentName))
                setIconifiedByDefault(false) // Do not iconify the widget; expand it by default
            }
            val searchItem = menu.findItem(R.id.searchBar)


            val searchView = searchItem.actionView as SearchView
            searchView.queryHint = "Search Fruit"
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.d(TAG, "onQueryTextSubmit: submitted")
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d(TAG, "onQueryTextChange: typing is happening")
                    adapter.countryList = adapter.fullDataSet.filter {
                        if (newText != null) {
                            it.name.lowercase().contains(newText.lowercase())

                        } else {
                            true
                        }
                    }
                    adapter.notifyDataSetChanged()
                    return true
                }
            })
            searchView.isIconified = false

            return true
        }


        fun getList(): List<Country> {
            return countryList
        }


        override fun onOptionsItemSelected(item: MenuItem): Boolean {

            return when (item.itemId) {
                R.id.name -> {
                    Toast.makeText(this, "Sorting by name", Toast.LENGTH_SHORT).show()
                    if (curList > 0) {
                        adapter.countryList = adapter.countryList.sortedBy {
                            it.name
                        }
                    }
                    if (curList < 0) {
                        adapter.countryList = CountryDetailActivity.favoriteList.sortedBy {
                            it.name
                        }
                    }
                    adapter.notifyDataSetChanged()
                    return true
                }
                R.id.population -> {
                    Toast.makeText(this, "Sorting by population", Toast.LENGTH_SHORT).show()
                    if (curList > 0) {
                        adapter.countryList = adapter.countryList.sortedByDescending {
                            it.population
                        }
                    }
                    if (curList < 0) {
                        adapter.countryList =
                            CountryDetailActivity.favoriteList.sortedByDescending {
                                it.population
                            }
                    }
                    adapter.notifyDataSetChanged()
                    return true
                }
                R.id.size -> {
                    Toast.makeText(this, "Sorting by size", Toast.LENGTH_SHORT).show()
                    if (curList > 0) {
                        adapter.countryList = adapter.countryList.sortedByDescending {
                            it.area
                        }
                    }
                    if (curList < 0) {
                        adapter.countryList =
                            CountryDetailActivity.favoriteList.sortedByDescending {
                                it.area
                            }
                    }
                    adapter.notifyDataSetChanged()
                    return true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
    }