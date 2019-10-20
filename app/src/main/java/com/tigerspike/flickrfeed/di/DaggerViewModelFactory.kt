package com.tigerspike.flickrfeed.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 *  ViewModelProvider for Dagger
 */
class DaggerViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(viewModelClass: Class<T>): T {
        val creator = creators[viewModelClass]
            ?: creators.entries.firstOrNull { viewModelClass.isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("unknown model class $viewModelClass")
        return creator.get() as T
    }
}
