package io.github.egumayuto.http

import io.github.egumayuto.util.HttpStringUtil
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * @author cabos
 */
class HttpRequest(inputStream: InputStream) {

    /** request line */
    val method: String
    val requestTarget: String
    val httpVersion: String
    /** request header */
    val requestHeaderMap: Map<String, String>

    init {
        val lineList = convertToMessageLineList(inputStream)
        val elementList = this.createRequestLineElementList(lineList[0])
        this.method = elementList[0]
        this.requestTarget = elementList[1]
        this.httpVersion = elementList[2]
        this.requestHeaderMap = this.createRequestHeaderMap(lineList)
    }

    private fun convertToMessageLineList(inputStream: InputStream): List<String> {
        val br = BufferedReader(InputStreamReader(inputStream))
        val lineList = ArrayList<String>()
        var line = br.readLine()
        while (line != null && !line.isEmpty()) {
            lineList.add(line)
            line = br.readLine()
        }
        return lineList
    }

    private fun createRequestLineElementList(firstLine: String): List<String> {
        val firstLineElementList = firstLine.split(HttpStringUtil.SP)
        if (firstLineElementList.size != 3) {
            throw IllegalArgumentException("incorrect http request. first line : $firstLine")
        }
        return firstLineElementList
    }

    private fun createRequestHeaderMap(lineList: List<String>): Map<String, String> {
        return lineList.subList(1, lineList.size - 1)
                .map ({ line -> line.split(":".toRegex(), 2) })
                .associateBy({ it[0] }, {it[1]})
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("${this.method} ${this.httpVersion} ${this.httpVersion}")
        this.requestHeaderMap.forEach({ entry ->
            sb.append("${entry.key}: ${entry.value}${HttpStringUtil.CRLF}")
        })
        return sb.toString()
    }
}