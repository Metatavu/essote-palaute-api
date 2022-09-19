package fi.metatavu.essote.palaute.api.impl

import fi.metatavu.spec.SurveysApi
import javax.ws.rs.core.Response

/**
 * API implementation for Surveys API
 */
class SurveysApi: SurveysApi, AbstractApi() {
    override suspend fun findSurvey(surveyName: String): Response {
        TODO("Not yet implemented")
    }

    override suspend fun findSurveyQuestionSummary(surveyName: String, questionNumber: Long): Response {
        TODO("Not yet implemented")
    }

    override suspend fun listSurveyQuestions(surveyName: String): Response {
        TODO("Not yet implemented")
    }

    override suspend fun listSurveys(): Response {
        TODO("Not yet implemented")
    }

}