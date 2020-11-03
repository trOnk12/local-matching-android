package com.example.local_matching.di

import com.example.local_matching.repository.LocationRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideLocationRepository(locationRepository: LocationRepository): ILocationRepository

}