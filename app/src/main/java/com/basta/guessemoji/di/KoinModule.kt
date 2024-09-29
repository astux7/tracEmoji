package com.basta.guessemoji.di

import com.basta.guessemoji.data.UserPreferenceRepositoryImp
import com.basta.guessemoji.presentation.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModule {

    fun allModule(): List<Module> =
        listOf(
            mainModule,
            viewModelModule,
            dataModule
        )

    private val mainModule: Module
        get() = module { }

    private val viewModelModule: Module
        get() = module {
            viewModel { SettingsViewModel() }
        }

    private val dataModule: Module
        get() = module {
            single {
                UserPreferenceRepositoryImp(get(), get())
            }
        }
}