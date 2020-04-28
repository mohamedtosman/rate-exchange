package com.example

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.html.*
import kotlinx.html.*
import validation.CurrencyCodeValidator
import kotlin.system.exitProcess

const val API_ENDPOINT = "/rates"
const val LATEST_ENDPOINT = "$API_ENDPOINT/latest"
const val HISTORICAL_DATE_ENDPOINT = "$API_ENDPOINT/historical/{date}"
const val HISTORICAL_RANGE_ENDPOINT = "$API_ENDPOINT/historical/range"


fun Application.main() {
    routing {
        /**
         * Endpoint for getting latest exchange rates
         */
        get(LATEST_ENDPOINT) {
            LOG.debug("HTTP_GET request for getting latest rates")

            if(call.parameters["symbols"] != null) {
                if(!validateSymbols(call)) {
                    call.respondHtml { body { h1 { +"Invalid symbol currency code" } } }
                    return@get
                }
            }

            if(call.parameters["base"] != null) {
                if(!validateBase(call)) {
                    call.respondHtml { body { h1 { +"Invalid base currency code" } } }
                    return@get
                }
            }

            // Assign query parameters and get them ready to be passed in to client url
            val symbols =  if (call.parameters["symbols"] != null) "?symbols="  + call.parameters["symbols"] else ""
            val base = if (call.parameters["base"] != null) "&base="  + call.parameters["base"] else ""

            // Response from the client using the above parameters
            val res = getLatest(symbols, base)

            // Return as an html
            call.respondHtml {
                body {
                    h1 { + "Latest Rates" }
                    + "Rates: "
                    + res.rates.toString()
                    hr {  }
                    + "Base: "
                    + res.base
                    hr {  }
                    + "Date: "
                    + res.date!!
                }
            }
        }

        /**
         * Endpoint for getting exchange rate on a specific date
         */
        get(HISTORICAL_DATE_ENDPOINT) {
            LOG.debug("HTTP_GET request for getting historical rates with given date")

            if(call.parameters["base"] != null) {
                if(!validateBase(call)) {
                    call.respondHtml { body { h1 { +"Invalid base currency code" } } }
                    return@get
                }
            }

            // Assign query parameters and get them ready to be passed in to client url
            val date = call.parameters["date"]!!
            val base = if (call.parameters["base"] != null) "&base="  + call.parameters["base"] else ""

            // Response from the client using the above parameters
            val res = getHistoricalDate(date, base)

            // Return as an html
            call.respondHtml {
                body {
                    h1 { + "Historical Date Rates"}
                    + "Rates: "
                    + res.rates.toString()
                    hr {  }
                    + "Base: "
                    + res.base
                    hr {  }
                    + "Date: "
                    + res.date!!
                }
            }
        }

        /**
         * Endpoint for getting exchange rate between 2 dates
         */
        get(HISTORICAL_RANGE_ENDPOINT) {
            LOG.debug("HTTP_GET request for getting historical rates between two dates")

            if(call.parameters["symbols"] != null) {
                if(!validateSymbols(call)) {
                    call.respondHtml { body { h1 { +"Invalid symbols currency code" } } }
                    return@get
                }
            }

            // Assign query parameters and get them ready to be passed in to client url
            val startDate = call.parameters["start_at"] ?: throw IllegalArgumentException("Parameter from_date not found")
            val endDate = call.parameters["end_at"] ?: throw IllegalArgumentException("Parameter end_date not found")
            val symbols =  if (call.parameters["symbols"] != null) "&symbols="  + call.parameters["symbols"] else ""

            // Response from the client using the above parameters
            val res = getHistoricalRange(startDate, endDate, symbols)

            // Return as an html
            call.respondHtml {
                body {
                    h1 { + "Historical Range Rates"}
                    + "Rates: "
                    + res.rates.toString()
                    hr {  }
                    + "Base: "
                    + res.base
                    hr {  }
                    + "Start At: "
                    + res.start_at!!
                    hr {  }
                    + "End At: "
                    + res.end_at!!
                }
            }
        }
    }
}

private fun validateSymbols(call: ApplicationCall): Boolean {
    var currencyValidator = true
    val symbolsArray = call.parameters["symbols"]!!.split(",").toTypedArray()

    symbolsArray.forEach {
        currencyValidator = CurrencyCodeValidator.validateCurrency(it)
        if (!currencyValidator) return currencyValidator
    }
    return currencyValidator
}

private fun validateBase(call: ApplicationCall): Boolean {
    return CurrencyCodeValidator.validateCurrency(call.parameters["base"])
}