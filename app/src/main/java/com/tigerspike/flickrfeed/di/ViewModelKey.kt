package com.tigerspike.flickrfeed.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Dagger Multibinding ViewModel Key
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
