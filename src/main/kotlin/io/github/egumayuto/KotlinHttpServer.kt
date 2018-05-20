package io.github.egumayuto

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
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
                    logger.info(getHttpRequest(it))
                    writeHttpResponse(it)
                }
            } catch (e: IOException) {
                logger.error("failuer http request handling", e)
            }
        } catch (e: Exception) {
            logger.error("failuer to accept http request", e)
        }
    }
}

fun getHttpRequest(socket: Socket): String {
    val br = BufferedReader(InputStreamReader(socket.getInputStream()))
        val sb = StringBuilder()
        var line = br.readLine()
        while (line != null && !line.isEmpty()) {
            sb.append(line).append("\n")
            line = br.readLine()
        }
        return sb.toString()
}

fun writeHttpResponse(socket: Socket) {
    socket.getOutputStream().use { outputStream ->
        val br = BufferedWriter(OutputStreamWriter(outputStream))
        br.write("HTTP/1.1 200 OK\n" +
                "Date: Sun, 11 Jan 2004 16:06:23 GMT\n" +
                "Content-Type: text/html\n" +
                "\n" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <body>Sample response</body>\n" +
                "</html>\n")
    }
}