package com.example

import io.ktor.application.*
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import org.junit.Test


class ApplicationTest {

    @Test
    fun getLatestRates(): Unit = withTestApplication(Application::main) {
        handleRequest(HttpMethod.Get, LATEST_ENDPOINT+"?symbols=USD") {
        }.response.let {
            assertEquals(HttpStatusCode.OK, it.status())
            assertNotNull(it.content)
            assertEquals("Base: EUR", it.content)
        }
    }
}
