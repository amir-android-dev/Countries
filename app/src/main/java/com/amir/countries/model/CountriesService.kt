package com.amir.countries.model

import com.amir.countries.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject


class CountriesService {
/*this is the main address and the rest is in CountriesAPI
 -we can write the whole url here, but if we have a main url and different endpoints, it is better to separate them like here
 -if we write the below url + / + the endpoint address in CountriesAPI, we will see the data as jason
 */

    @Inject
    lateinit var api: CountriesAPI

    //here we create the API
    init {
        DaggerApiComponent.create().inject(this)
    }

    //here we use it
    fun getCountries(): Single<List<Country>> {
        return api.getCountries()
    }
}