package com.basta.guessemoji.di

import com.basta.guessemoji.data.GameRepositoryImpl
import com.basta.guessemoji.data.UserPreferenceRepositoryImp
import com.basta.guessemoji.domain.repository.GameRepository
import com.basta.guessemoji.domain.repository.UserPreferenceRepository
import com.basta.guessemoji.presentation.UserUseCase
import com.basta.guessemoji.presentation.earn.EarnViewModel
import com.basta.guessemoji.presentation.game.GameUseCase
import com.basta.guessemoji.presentation.game.GameViewModel
import com.basta.guessemoji.presentation.play.HomeViewModel
import com.basta.guessemoji.presentation.profile.ProfileViewModel
import com.basta.guessemoji.presentation.settings.SettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModule {
    fun allModule(): List<Module> =
        listOf(
            dataModule,
            useCaseModule,
            viewModelModule,
        )

    private val useCaseModule: Module
        get() = module {
            factory { GameUseCase(get()) }
            single { UserUseCase(get()) }
        }

    private val viewModelModule: Module
        get() = module {
//
//            single<Activity> {
//                val activity = (get<Application>() as GuessEmojiApplication).getActivityOrNull()
//                activity ?: throw IllegalStateException("No active Activity found")
//            }

            viewModel { SettingsViewModel() }
            viewModel { GameViewModel(get(), get()) }
            viewModel { ProfileViewModel(get()) }
            viewModel { HomeViewModel(get()) }
            viewModel { EarnViewModel(get()) }
        }

    private val dataModule: Module
        get() = module {
            single<UserPreferenceRepository> { UserPreferenceRepositoryImp(get(), get()) }
            single<CoroutineScope> {
                CoroutineScope(SupervisorJob() + Dispatchers.IO)
            }
            single<GameRepository> { GameRepositoryImpl() }
        }
}