package fi.metatavu.essote.palaute.api.utils

import org.slf4j.Logger
import java.io.File
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

/**
 * Utility class for File IO operations
 */
@ApplicationScoped
class FileUtils {

    @Inject
    lateinit var logger: Logger

    /**
     * Reads file from given path and returns it as a string
     *
     * @param filePath String
     * @return String
     */
    fun readFromFile(filePath: String): String {
        try {
            val file = File(filePath).inputStream().readBytes()

            file.inputStream().close()

            return file.toString(Charsets.UTF_8)
        } catch (e: Error) {
            logger.error("Error while opening file $filePath: ${e.localizedMessage}")
            throw e
        }
    }
}