package dev.aurakai.auraframefx.buildlogic

import java.io.File

object TestUtils {
    /**
     * Writes the given text to a file located at [dir]/[relativePath], creating any missing parent directories.
     *
     * The target file is created if it does not exist or overwritten if it does.
     *
     * @param dir Base directory for the target file.
     * @param relativePath Path relative to [dir] where the file will be written.
     * @param content Text content to write to the file.
     * @return The File instance representing the created or updated file.
     */
    fun writeFile(dir: File, relativePath: String, content: String): File {
        val file = File(dir, relativePath)
        file.parentFile?.mkdirs()
        file.writeText(content)
        return file
    }
}