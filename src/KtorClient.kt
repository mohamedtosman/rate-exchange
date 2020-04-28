package com.example

import com.example.domain.RangeResponse
import com.example.domain.RatesResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get

// API main url
const val API_URL = "https://api.exchangeratesapi.io/"

// Client used to create requests to reach the exchange api
val client = HttpClient(Apache) {
    install(JsonFeature) {
        serializer = GsonSerializer()
    }
}

/**
 * Returns response for latest rates
 *
 * @param symbols represents a currency
 * @param base represents the base currency
 * @return response from client as a string
 */
suspend fun getLatest(symbols: String, base: String): RatesResponse {
    // Create url and send a get request to client
    return client.get(API_URL + "latest" + symbols + base)
}

/**
 * Returns response for historical rates on a specific date
 *
 * @param date represents a date to grab rates on
 * @param base represents the base currency
 * @return response from client as a string
 */
suspend fun getHistoricalDate(date: String, base: String): RatesResponse {
    // Create url and send a get request to client
    return client.get(API_URL + date + "?base=" + base)
}

/**
 * Returns response for historical rates between 2 dates
 *
 * @param startDate represents start date for the range
 * @param endDate represents the end date for the range
 * @param symbols represents a currency
 * @return response from client as a string
 */
suspend fun getHistoricalRange(startDate: String, endDate: String, symbols: String): RangeResponse {
    // Create url and send a get request to client
    return client.get(API_URL + "history?start_at=" + startDate + "&end_at=" + endDate + symbols)
}