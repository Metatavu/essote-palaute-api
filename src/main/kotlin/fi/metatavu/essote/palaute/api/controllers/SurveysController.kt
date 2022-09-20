package fi.metatavu.essote.palaute.api.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import fi.metatavu.essote.palaute.api.bisnode.BisnodeService
import fi.metatavu.model.Survey
import fi.metatavu.model.SurveyQuestion
import fi.metatavu.model.SurveyQuestionSummary
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.slf4j.Logger
import java.io.File
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

/**
 * Controller for Surveys
 */
@ApplicationScoped
class SurveysController {

    @Inject
    lateinit var bisnodeService: BisnodeService

    @Inject
    lateinit var logger: Logger

    @ConfigProperty(name = "surveys.file")
    private lateinit var surveyFilePath: String

    /**
     * Gets a list of surveys
     *
     * @return List of Surveys
     */
    fun listSurveys(): Array<Survey> {
        return try {
            jacksonObjectMapper().readValue(readFromFile(surveyFilePath), Array<Survey>::class.java)
        } catch (e: Error) {
            logger.error("Error while opening file $surveyFilePath: ${e.localizedMessage}")
            throw e
        }
    }

    /**
     * Gets a list of survey questions
     *
     * @param surveyName survey name
     * @return List of SurveyQuestion or null
     */
    fun listSurveyQuestions(surveyName: String): List<SurveyQuestion>? {
        val survey = listSurveys().find { it.name == surveyName }
            ?: return null

        return survey.questions
    }

    /**
     * Finds a single survey question by survey name and question number
     *
     * @param surveyName survey name
     * @param questionNumber survey question number
     * @return SurveyQuestion or null
     */
    fun findSurveyQuestionBySurveyNameAndNumber(surveyName: String, questionNumber: Long): SurveyQuestion? {
        val survey = listSurveyQuestions(surveyName)
            ?: return null

        return survey.find { it.number == questionNumber }
    }

    /**
     * Finds survey question summary by survey name and question number
     *
     * @param surveyName survey name
     * @param questionNumber survey question number
     * @return SurveyQuestionSummary or null
     */
    fun findSurveyQuestionSummary(surveyName: String, questionNumber: Long): SurveyQuestionSummary {
        return bisnodeService.getSurveyQuestionSummary(
            surveyName = surveyName,
            questionNumber = questionNumber
        )
    }

    /**
     * Finds survey by name
     *
     * @param surveyName survey name
     * @return Survey or null
     */
    fun findSurveyByName(surveyName: String): Survey? {
        return listSurveys().find { it.name == surveyName }
    }

    private fun readFromFile(filePath: String): String {
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