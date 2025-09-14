package dev.aurakai.auraframefx.buildlogic

import java.io.File

object TestUtils {
    fun writeFile(dir: File, relativePath: String, content: String): File {
        val file = File(dir, relativePath)
        file.parentFile?.mkdirs()
        file.writeText(content)
        return file
    }
}