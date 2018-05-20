package io.github.egumayuto.http

/**
 * @author cabos
 */
private const val CR = "\r"
private const val LF = "\n"
private const val CRLF = CR + LF
private const val SP = " "

class HttpResponse(private val httpStatus: HttpStatus, private val body: String) {

    private val httpVersion: String = "HTTP/1.1"
    private val responseHeaderMap: Map<String, String> = mapOf(Pair("Content-Type", "text/html; charset=UTF-8"), Pair("Content-Length", body.length.toString()))

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("$httpVersion$SP${httpStatus.code}$SP$httpStatus")
        this.responseHeaderMap.forEach({ entry ->
            sb.append("${entry.key}: ${entry.value}$CRLF")
        })
        sb.append(CRLF)
        sb.append(body)
        return sb.toString()
    }
}