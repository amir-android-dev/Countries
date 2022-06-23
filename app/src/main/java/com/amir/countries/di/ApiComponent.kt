package com.amir.countries.di

import com.amir.countries.model.CountriesService
import com.amir.countries.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [APIModule::class] )
interface ApiComponent {
//this function will help Daggar inject the right components from API module into  countries service
    fun inject(service: CountriesService)

    fun inject(viewModel: ListViewModel)
}