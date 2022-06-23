package com.amir.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amir.countries.di.DaggerApiComponent
import com.amir.countries.model.CountriesService
import com.amir.countries.model.Country
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/*ViewModel is part of the Android lifecycle architecture.
/it is created by Google to make it very easy for us to implement this kind of architecture.
 */
/*
 the way this is going to work is the view model is going to have three variables and these variables
are what we call live data.
 */
/*
live data is a variable that anyone can subscribe to and can access the data in real time.So when this variable updates,
then all the subscribers to that variable will get notified.
 */
class ListViewModel : ViewModel() {
    //to get the info from service /private val countriesService = CountriesService()
    @Inject
    lateinit var countriesService:CountriesService
    init {
        DaggerApiComponent.create().inject(this)
    }
    //When this view model is closed, we need to close or clear that connection.
    private val disposable = CompositeDisposable()
    val countries = MutableLiveData<List<Country>>()

    //if there's an error in loading
    val countryLoadError = MutableLiveData<Boolean>()

    /*this variable will tell us whether it is the view model or the list of your model is in the process
    //of loading the data from the back end.*/
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        //we notify that the loading is starting
        //Single is basically an observable that only omits one value and then closes.
       // loading.value = true
        disposable.add(
            countriesService.getCountries()  //calling the getCountries from countryService
                .subscribeOn(Schedulers.newThread())//we're subscribing we're doing the processes on a separate thread, but we're getting all the information on the main thread
                .observeOn(AndroidSchedulers.mainThread())//the thread that the user see
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    //we need to define what we're going to do when we get the information.
                    override fun onSuccess(value: List<Country>) {
                        countries.value = value
                        countryLoadError.value =false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        countryLoadError.value =true
                        loading.value = false
                    }
                })
        )

//        val mockData = listOf(
//            Country("CountryA"),
//            Country("Country1"),
//            Country("Country2")
//        )
//        //countryLoadError.value = false : it means i have had no error in loading data, so i need to notify all the subscribes
//        countryLoadError.value = false
//        //, then I'm going to say loading that value equals false because it's not loading and I'm going to
//        //say countries.
//        loading.value= false
//        countries.value =mockData

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}