package io.github.egumayuto

import io.github.egumayuto.http.HttpRequest
import io.github.egumayuto.http.HttpResponse
import io.github.egumayuto.http.handle
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket

/**
 * @author cabos
 */
private const val portNumber: Int = 8090
private val logger: Logger = LogManager.getLogger("main")

fun main(args: Array<String>) {

    logger.info("Sample Server Start \n")
    val serverSocket = ServerSocket(portNumber)

    while (true) {
        try {
            val socket = serverSocket.accept()
            try {
                socket.use {
                    val request = getHttpRequest(it)
                    logger.info(request)
                    val response = handle(request)
                    logger.info(response.responseHeaderString)
                    writeHttpResponse(socket, response)
                }
            } catch (e: IOException) {
                logger.error("failure http request handling", e)
            }
        } catch (e: Exception) {
            logger.error("failure to accept http request", e)
        }
    }
}

fun getHttpRequest(socket: Socket): HttpRequest {
    return HttpRequest(socket.getInputStream())
}

fun writeHttpResponse(socket: Socket, response: HttpResponse) {
    socket.getOutputStream().use { outputStream ->
        outputStream.write(response.binaryResponse)
    }
}