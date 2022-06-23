package com.amir.countries.di


import com.amir.countries.model.CountriesAPI
import com.amir.countries.model.CountriesService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//the way dependency injection works is that we need to annotate this class to let Dagger know that
//this is a class that is a module and will provide the API
@Module
class APIModule {

    private val BASE_URL = "https://raw.githubusercontent.com"



    //this module will provide this country's API.
    @Provides
    fun provideCountriesApi(): CountriesAPI {
        return Retrofit.Builder()  //creating a framework for retrofit to get the info from backend
            .baseUrl(BASE_URL)  //here we need the base url(main url)
            .addConverterFactory(GsonConverterFactory.create()) //ok, we get the info as gson( the info is in json)look at the country data class, this line of code get the info and convert it to kotlin code
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//this line will convert the code, that country code, into an observable variable
            .build()
            .create(CountriesAPI::class.java)
    }


    @Provides
    fun provideCountriesService(): CountriesService {
        return CountriesService()

    }
}