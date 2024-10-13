package com.basta.guessemoji.di

import com.basta.guessemoji.data.GameRepositoryImpl
import com.basta.guessemoji.data.UserPreferenceRepositoryImp
import com.basta.guessemoji.domain.repository.GameRepository
import com.basta.guessemoji.domain.repository.UserPreferenceRepository
import com.basta.guessemoji.presentation.game.GameUseCase
import com.basta.guessemoji.presentation.game.GameViewModel
import com.basta.guessemoji.presentation.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModule {

    fun allModule(): List<Module> =
        listOf(
            useCaseModule,
            viewModelModule,
            dataModule
        )

    private val useCaseModule: Module
        get() = module {
            factory { GameUseCase(get()) }
        }

    private val viewModelModule: Module
        get() = module {
            viewModel { SettingsViewModel() }
            viewModel { GameViewModel(get()) }
        }

    private val dataModule: Module
        get() = module {
            single<GameRepository> { GameRepositoryImpl() }
            single<UserPreferenceRepository> {
                UserPreferenceRepositoryImp(get(), get())
            }
        }
}