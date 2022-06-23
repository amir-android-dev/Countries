package com.amir.countries.model

import io.reactivex.Single
import retrofit2.http.GET

interface CountriesAPI {
    //a function in order to retrieve the information from backend
    //in ( ) we have to specify the end point
    //after we create this function we have to create a service to use this interface and call the backend endpoint to get information
    //Single is basically unobservable that only omits one value and then closes.
    @GET("DevTides/countries/master/countriesV2.json")
    fun getCountries(): Single<List<Country>>
}