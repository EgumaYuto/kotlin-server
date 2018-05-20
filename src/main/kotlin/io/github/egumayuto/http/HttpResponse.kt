package io.github.egumayuto.http

import io.github.egumayuto.util.HttpStringUtil

/**
 * @author cabos
 */
class HttpResponse(private val httpStatus: HttpStatus, private val body: String) {

    private val httpVersion: String = "HTTP/1.1"
    private val responseHeaderMap: Map<String, String> = mapOf(Pair("Content-Type", "text/html; charset=UTF-8"), Pair("Content-Length", body.length.toString()))

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("$httpVersion${HttpStringUtil.SP}${httpStatus.code}${HttpStringUtil.SP}$httpStatus")
        this.responseHeaderMap.forEach({ entry ->
            sb.append("${entry.key}: ${entry.value}${HttpStringUtil.CRLF}")
        })
        sb.append(HttpStringUtil.CRLF)
        sb.append(body)
        return sb.toString()
    }
}