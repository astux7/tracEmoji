package com.basta.guessemoji

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.basta.guessemoji.presentation.MainScreen
import com.basta.guessemoji.ui.theme.MyApplicationTheme
import android.provider.Settings
import android.util.Log
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import uk.fernando.advertising.MyAdvertising
import java.util.concurrent.atomic.AtomicBoolean

class MainActivity : ComponentActivity() {
    private lateinit var consentInformation: ConsentInformation
    // Use an atomic boolean to initialize the Google Mobile Ads SDK and load ads once.
    private var isMobileAdsInitializeCalled = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))

        setContent {
            MyApplicationTheme {
                MainScreen()
            }
            requestForConsent()
        }
    }

    private fun requestForConsent() {
        // Set tag for under age of consent. false means users are not under age
        // of consent.
        val params = ConsentRequestParameters
            .Builder()
            .setTagForUnderAgeOfConsent(false)
            .build()

        consentInformation = UserMessagingPlatform.getConsentInformation(this)
        consentInformation.requestConsentInfoUpdate(
            this,
            params,
            ConsentInformation.OnConsentInfoUpdateSuccessListener {
                loadAndShowConsentIfRequired()
            },
            ConsentInformation.OnConsentInfoUpdateFailureListener { requestConsentError ->
                // Consent gathering failed.
                Log.d(
                    "CONSENT", String.format(
                        "%s: %s",
                        requestConsentError.errorCode,
                        requestConsentError.message
                    )
                )
            })

        // Check if you can initialize the Google Mobile Ads SDK in parallel
        // while checking for new consent information. Consent obtained in
        // the previous session can be used to request ads.
        if (consentInformation.canRequestAds()) {
            initializeMobileAdsSdk()
        }
    }

    private fun loadAndShowConsentIfRequired() {
        UserMessagingPlatform.loadAndShowConsentFormIfRequired(
            this@MainActivity
        ) { loadAndShowError ->
            // Consent gathering failed.
            Log.d(
                "CONSENT-REQUIRED", String.format(
                    "%s: %s",
                    loadAndShowError?.errorCode,
                    loadAndShowError?.message
                )
            )
            // Consent has been gathered.
            if (consentInformation.canRequestAds()) {
                initializeMobileAdsSdk()
            }
        }
    }

    private fun initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.get()) {
            return
        }
        isMobileAdsInitializeCalled.set(true)

        // Initialize the Google Mobile Ads SDK.
        setupAds()
    }

    private fun setupAds() {
        val id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID) ?: ""
        MyAdvertising.setDeviceID(listOf(id))
        MyAdvertising.initialize(this)
    }
}