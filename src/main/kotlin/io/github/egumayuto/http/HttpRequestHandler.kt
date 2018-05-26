package io.github.egumayuto.http

import io.github.egumayuto.contents.ContentsLoadResultType
import io.github.egumayuto.contents.loadContents

/**
 * @author cabos
 */
fun handle(request: HttpRequest): HttpResponse {

    val loadResult = loadContents(request.requestTarget)
    val data = loadResult.contents.data
    return when (loadResult.loadResultType) {
        ContentsLoadResultType.loadSuccess -> HttpResponse(HttpStatus.OK, data)
        ContentsLoadResultType.forbidden -> HttpResponse(HttpStatus.FORBIDDEN, data)
        ContentsLoadResultType.notExists -> HttpResponse(HttpStatus.NOT_FOUND, data)
        ContentsLoadResultType.loadFailure -> HttpResponse(HttpStatus.OK, data)
    }
}