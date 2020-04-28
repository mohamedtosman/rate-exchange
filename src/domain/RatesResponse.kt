package com.example.domain

/**
 * Data class that represents the response from the client API
 */
data class RatesResponse(
    val rates: Map<String, Double>,

    val base: String,

    val date: String?
)