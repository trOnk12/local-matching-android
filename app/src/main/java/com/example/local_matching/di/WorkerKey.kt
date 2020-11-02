package com.example.local_matching.di

import androidx.work.CoroutineWorker
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out CoroutineWorker>)
