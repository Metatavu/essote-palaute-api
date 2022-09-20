package fi.metatavu.essote.palaute.api.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import fi.metatavu.essote.palaute.api.bisnode.BisnodeService
import fi.metatavu.model.Survey
import fi.metatavu.model.SurveyQuestion
import org.eclipse.microprofile.config.ConfigProvider
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

//    @Inject
//    lateinit var logger: Logger

    @ConfigProperty(name = "surveys.file")
    private lateinit var surveyFilePath: String

    /**
     * Gets a list of surveys
     *
     * @return List of Surveys
     */
    fun listSurveys(): Array<Survey> {
        try {
            val surveyFile = File(surveyFilePath).inputStream().readBytes()
            val surveys = jacksonObjectMapper().readValue(surveyFile.toString(Charsets.UTF_8), Array<Survey>::class.java)

            surveyFile.inputStream().close()

            return surveys
        } catch (e: Error) {
//            logger.error("Error while opening file $surveyFilePath: ${e.localizedMessage}")
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
     * Finds survey by name
     *
     * @param surveyName survey name
     * @return Survey or null
     */
    fun findSurveyByName(surveyName: String): Survey? {
        return listSurveys().find { it.name == surveyName }
    }
}