package com.basta.guessemoji

import android.app.Application
import com.basta.guessemoji.di.KoinModule
import com.basta.guessemoji.domain.repository.UserPreferenceRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class GuessEmojiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GuessEmojiApplication)
            modules(KoinModule.allModule())
        }.also {
            it.koin.get<UserPreferenceRepository>()
        }
    }
}
