package io.github.egumayuto

import io.github.egumayuto.contents.loadContents
import io.github.egumayuto.http.HttpRequest
import io.github.egumayuto.http.HttpResponse
import io.github.egumayuto.http.HttpStatus
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter
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
                    writeHttpResponse(it, request)

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

fun writeHttpResponse(socket: Socket, request: HttpRequest) {
    socket.getOutputStream().use { outputStream ->
        outputStream.write(HttpResponse(HttpStatus.OK, loadContents(request.requestTarget).contents.data).binaryResponse)
    }
}