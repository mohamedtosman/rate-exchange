package com.example.domain

import com.google.gson.JsonObject

/**
 * Data class that represents the response from the client API
 */
data class RatesResponse(
    val rates: JsonObject,

    val base: String,

    val date: String?,

    val start_at: String?,

    val end_at: String?
)