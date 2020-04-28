package com.example.domain

/**
 * Data class that represents the response from the client API
 */
data class RangeResponse(
    val rates: Map<String, Map<String, Double>>,

    val start_at: String,

    val end_at: String,

    val base: String
)