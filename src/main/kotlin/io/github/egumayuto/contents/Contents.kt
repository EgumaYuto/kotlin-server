package io.github.egumayuto.contents

/**
 * @author cabos
 */
class Contents(val filePath: String, val data: ByteArray = ByteArray(0)) {

    val extension: String
        get() {
            val stringsByPeriod = this.filePath.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return stringsByPeriod[stringsByPeriod.lastIndex]
        }
}