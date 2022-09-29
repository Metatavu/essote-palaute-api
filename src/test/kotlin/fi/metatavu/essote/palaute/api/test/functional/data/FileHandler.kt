package fi.metatavu.essote.palaute.api.test.functional.data

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import fi.metatavu.model.ReviewProduct
import fi.metatavu.model.Survey
import java.io.File

/**
 * Class for reading from files in tests
 */
class FileHandler {

    companion object {

        /**
         * Gets mock surveys
         *
         * @return List of Surveys
         */
        fun getSurveys(): List<Survey> {
            return jacksonObjectMapper().readValue(readFile("src/test/resources/surveys.json"))
        }

        /**
         * Gets mock review products
         *
         * @return List of ReviewProducts
         */
        fun getReviewProducts(): List<ReviewProduct> {
            return jacksonObjectMapper().readValue(readFile("src/test/resources/products.json"))
        }

        /**
         * Opens file in given file path and returns its content as string.
         *
         * @param filePath String
         * @return String
         */
        private fun readFile(filePath: String): String {
            try {
                val file = File(filePath).inputStream().readBytes()

                file.inputStream().close()

                return file.toString(Charsets.UTF_8)
            } catch (e: Error) {
                throw e
            }
        }
    }
}