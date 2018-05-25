package io.github.egumayuto.http

import io.github.egumayuto.util.HttpStringUtil

/**
 * @author cabos
 */
class HttpResponse(val httpStatus: HttpStatus, val body: ByteArray) {

    private val httpVersion: String = "HTTP/1.1"
    private val responseHeaderMap: Map<String, String> = mapOf(Pair("Content-Type", "text/html; charset=UTF-8"), Pair("Content-Length", body.size.toString()))

    val binaryResponse: ByteArray
        get() {
            val header = "$responseHeaderString${HttpStringUtil.CRLF}".toByteArray()
            return header + body
        }

    val responseHeaderString: String
        get() {
            val sb = StringBuilder()
            sb.append("$httpVersion${HttpStringUtil.SP}${httpStatus.code}${HttpStringUtil.SP}$httpStatus")
            this.responseHeaderMap.forEach({ entry ->
                sb.append("${entry.key}: ${entry.value}${HttpStringUtil.CRLF}")
            })
            return sb.toString()
        }
}