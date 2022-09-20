package fi.metatavu.essote.palaute.api.impl

import fi.metatavu.essote.palaute.api.controllers.SurveysController
import fi.metatavu.spec.SurveysApi
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.core.Response

/**
 * API implementation for Surveys API
 */
@RequestScoped
class SurveysApi: SurveysApi, AbstractApi() {

    @Inject
    lateinit var surveysController: SurveysController

    override suspend fun findSurvey(surveyName: String): Response {
        return try {
            val survey = surveysController.findSurveyByName(surveyName)
                ?: return createNotFound("No survey found for $surveyName")

            createOk(survey)
        } catch (e: Error) {
            createInternalServerError(e.localizedMessage)
        }
    }

    override suspend fun findSurveyQuestionSummary(surveyName: String, questionNumber: Long): Response {
        return try {
            surveysController.findSurveyQuestionBySurveyNameAndNumber(
                surveyName = surveyName,
                questionNumber = questionNumber
            )
                ?: return createNotFound("No survey for $surveyName or question for $questionNumber found")

            createOk(surveysController.findSurveyQuestionSummary(
                surveyName = surveyName,
                questionNumber = questionNumber
            ))
        } catch (e: Error) {
            createInternalServerError(e.localizedMessage)
        }
    }

    override suspend fun listSurveyQuestions(surveyName: String): Response {
        return try {
            val surveyQuestions = surveysController.listSurveyQuestions(surveyName)
                ?: return createNotFound("No survey found for $surveyName")

            createOk(surveyQuestions)
        } catch (e: Error) {
            createInternalServerError(e.localizedMessage)
        }
    }

    override suspend fun listSurveys(): Response {
        return try {
            createOk(surveysController.listSurveys())
        } catch (e: Error) {
            createInternalServerError(e.localizedMessage)
        }
    }

}