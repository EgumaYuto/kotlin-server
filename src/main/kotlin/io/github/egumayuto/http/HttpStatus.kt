package io.github.egumayuto.http

/**
 * @author cabos
 */
enum class HttpStatus(val code: Int) {
    OK(200),
    FORBIDDEN(304),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500)
}