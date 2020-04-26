package com.example

import io.ktor.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.slf4j.Logger
import org.slf4j.LoggerFactory

// Used for logging on server side
val LOG: Logger = LoggerFactory.getLogger("ktor-server")

fun main() {
    LOG.debug("Starting ktor server.")

    // Create and start ktor server using embedded Netty
    embeddedServer(Netty, port = 8080, host = "127.0.0.1", module = Application::main).start(wait = true)
}