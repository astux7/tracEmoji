package com.basta.guessemoji.domain.model

enum class CreditType(val credit: Int) {
    ADS(10),
    FAIL(-5),
    TIMEOUT(-5),
    UNLOCK(-50),
    WELCOME(20)
}