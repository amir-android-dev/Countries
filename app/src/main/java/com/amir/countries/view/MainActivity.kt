package com.amir.countries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amir.countries.R
import com.amir.countries.databinding.ActivityMainBinding
import com.amir.countries.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {
    //late init var : it's kind of a promise from the developer that they will instantiate this variable before it's actually used.
    private lateinit var viewModel: ListViewModel

    private val countriesAdapter = CountryListAdapter(arrayListOf())
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //deprecated : https://stackoverflow.com/questions/57534730/as-viewmodelproviders-of-is-deprecated-how-should-i-create-object-of-viewmode
        // viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        /*
        this is a very Android Life-cycle typical way of instantiating a view model.
        And the reason we do that is because the Android system in the background will take care to update this
        view model and, you know, destroy it when we don't need it.
         */
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        binding?.countriesList?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter= countriesAdapter
        }
        binding?.swipeRefreshLayout?.setOnRefreshListener {
            binding?.swipeRefreshLayout?.isRefreshing = false
            viewModel.refresh()
        }
        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, Observer { countries ->
            //let means if countries is not null then do this
            //the "it" refers to country
            countries?.let {
                binding?.countriesList?.visibility = View.VISIBLE
                countriesAdapter.updateCountryList(it) }
        })

        viewModel.countryLoadError.observe(this, Observer {
            //the "it" refers to isError
                isError ->
            isError?.let { binding?.listError?.visibility = if (it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                binding?.loadingView?.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding?.listError?.visibility = View.GONE
                    binding?.countriesList?.visibility = View.GONE
                }
            }
        })
    }
}