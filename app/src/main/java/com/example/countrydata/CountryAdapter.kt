package com.example.countrydata

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView

class CountryAdapter(var countryList: List<Country>) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewCountry: TextView
        val layout : ConstraintLayout

        init {
            textViewCountry = view.findViewById(R.id.item_countryName)
            layout = view.findViewById(R.id.layout_country)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_country, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val country = countryList[position]
        viewHolder.textViewCountry.text = country.name
        viewHolder.layout.setOnClickListener{
            val context = viewHolder.layout.context
            val vaccinationDetailIntent = Intent(context, CountryDetailActivity::class.java).apply {
                putExtra(CountryDetailActivity.EXTRA_COUNTRY, country)
            }
            context.startActivity(vaccinationDetailIntent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = countryList.size

}