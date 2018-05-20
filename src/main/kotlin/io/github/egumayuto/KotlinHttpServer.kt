package io.github.egumayuto

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.net.ServerSocket

/**
 * @author cabos
 */
private const val portNumber: Int = 8090
private val logger: Logger = LogManager.getLogger("main")

fun main(args: Array<String>) {

    logger.info("Sample Server Start \n")
    val serverSocket = ServerSocket(portNumber)

    while (true) {

        val socket = serverSocket.accept()
        logger.info("accepted : $socket")

        val os = socket.getOutputStream()
        os.bufferedWriter().write("Sample response\n")

        os.close()
        socket.close()
    }
}