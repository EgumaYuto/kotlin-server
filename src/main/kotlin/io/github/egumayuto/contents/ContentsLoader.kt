package io.github.egumayuto.contents

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author cabos
 */
private val CONTENTS_ROOT: String = File("./").canonicalPath + "/src/main/resources/public"

fun loadContents(contentsPath: String): ContentsLoadResult {
    val file = File(CONTENTS_ROOT + contentsPath)
    try {
        if (file.isFile) {
            return loadContentsIfCanRead(file)
        } else if (file.isDirectory) {
            val indexFile = File(file.canonicalPath + "/index.html")
            if (indexFile.isFile) {
                return loadContentsIfCanRead(indexFile)
            }
        }
        return ContentsLoadResult(Contents(file.canonicalPath), ContentsLoadResultType.notExists)
    } catch (e: IOException) {
        return ContentsLoadResult(Contents(file.canonicalPath), ContentsLoadResultType.loadFailure)
    }
}

private fun loadContentsIfCanRead(file: File): ContentsLoadResult {
    val filePath = file.canonicalPath
    return if (file.canRead()) {
        ContentsLoadResult(Contents(filePath, Files.readAllBytes(Paths.get(filePath))), ContentsLoadResultType.loadSuccess)
    } else {
        ContentsLoadResult(Contents(filePath), ContentsLoadResultType.forbidden)
    }
}