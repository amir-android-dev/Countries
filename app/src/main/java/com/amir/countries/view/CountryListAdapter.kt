package com.amir.countries.view
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amir.countries.R
import com.amir.countries.model.Country
import com.amir.countries.util.getProgressDrawable
import com.amir.countries.util.loadImage

class CountryListAdapter(var countries: ArrayList<Country>) :
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
/*this is very important because  it tells the Android system we have a new list of countries.
You have to redo or reinitialize the whole list.
 */
        notifyDataSetChanged()
    }

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCountryName: TextView = itemView.findViewById<TextView>(R.id.tv_name)
        private val tvCapital: TextView = itemView.findViewById<TextView>(R.id.tv_capital)
        private val ivFlag: ImageView = itemView.findViewById<ImageView>(R.id.iv_flag)
        private val progressDrawable= getProgressDrawable(itemView.context)

        fun bind(country: Country) {
            tvCountryName.text = country.countryName
            tvCapital.text = country.capital
            ivFlag.loadImage(country.flag,progressDrawable)

        }
    }


}