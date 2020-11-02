package com.example.local_matching.di

import com.example.local_matching.location.worker.ChildWorkerFactory
import com.example.local_matching.location.worker.LocationWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerBindingModule {

    @Binds
    @IntoMap
    @WorkerKey(LocationWorker::class)
    fun bindHelloWorldWorker(factory: LocationWorker.Factory): ChildWorkerFactory

}
