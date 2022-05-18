package com.example.countrydata

import android.os.Parcelable
import androidx.core.view.ContentInfoCompat
import kotlinx.parcelize.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class Country(var name: String,
                   var capital: String?,
                   var population: Long,
                   var area: Double,
                   var alpha2Code: String,
                   var region: String,
                   var language: String,
                   var isFavorite : Boolean
                   ): Parcelable, Comparable<Country> {
    override fun compareTo(other: Country): Int {
       if (this.isFavorite && !other.isFavorite){
           return 1
       }
        if (!this.isFavorite && other.isFavorite){
            return -1
        }
        else{
            return 0
        }
    }
}