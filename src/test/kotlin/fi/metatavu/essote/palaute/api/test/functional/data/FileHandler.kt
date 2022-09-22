package fi.metatavu.essote.palaute.api.test.functional.data

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import fi.metatavu.model.ReviewProduct
import fi.metatavu.model.Survey
import java.io.File

class FileHandler {

    companion object {

        fun getSurveys(): Array<Survey> {
            return jacksonObjectMapper().readValue(readFile("src/test/resources/surveys.json"), Array<Survey>::class.java)
        }

        fun getReviewProducts(): Array<ReviewProduct> {
            return jacksonObjectMapper().readValue(readFile("src/test/resources/products.json"), Array<ReviewProduct>::class.java)
        }

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