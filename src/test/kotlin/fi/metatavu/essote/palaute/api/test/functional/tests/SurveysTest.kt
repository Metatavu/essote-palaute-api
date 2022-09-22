package fi.metatavu.essote.palaute.api.test.functional.tests

import fi.metatavu.essote.palaute.api.test.functional.data.FileHandler
import fi.metatavu.essote.palaute.api.test.functional.resources.LocalTestProfile
import fi.metatavu.essote.palaute.api.test.functional.resources.TestWiremockResource
import fi.metatavu.model.Survey
import fi.metatavu.model.SurveyQuestion
import fi.metatavu.model.SurveyQuestionSummary
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.TestProfile
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Tests for Surveys API
 */
@QuarkusTest
@QuarkusTestResource(TestWiremockResource::class)
@TestProfile(LocalTestProfile::class)
class SurveysTest {

    @Test
    fun testFindSurvey() {
        val expectedSurvey = FileHandler.getSurveys().find { it.name == "emergency" }
        val response = RestAssured.given()
            .`when`().get("/v1/surveys/emergency")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(Survey::class.java)

        Assertions.assertEquals(response, expectedSurvey)
    }

    @Test
    fun testFindSurveyNotFound() {
        RestAssured.given()
            .`when`().get("/v1/surveys/nonExistingSurvey")
            .then()
            .statusCode(404)
            .body(containsString("No survey found for nonExistingSurvey"))
    }

    @Test
    fun testFindSurveyQuestionSummary() {
        val response = RestAssured.given()
            .`when`().get("/v1/surveys/emergency/questions/1/summary")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(SurveyQuestionSummary::class.java)

        Assertions.assertEquals(
            response,
            SurveyQuestionSummary(
                positive = 92,
                negative = 8,
                total = 100
            )
        )
    }

    @Test
    fun testFindSurveyQuestionSummaryNotFound() {
        RestAssured.given()
            .`when`().get("/v1/surveys/nonExistingSurvey/questions/1/summary")
            .then()
            .statusCode(404)
            .body(containsString("No survey for nonExistingSurvey or question for 1 found"))

        RestAssured.given()
            .`when`().get("/v1/surveys/emergency/questions/4/summary")
            .then()
            .statusCode(404)
            .body(containsString("No survey for emergency or question for 4 found"))
    }

    @Test
    fun testListSurveyQuestions() {
        val response = RestAssured.given()
            .`when`().get("/v1/surveys/emergency/questions")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(Array<SurveyQuestion>::class.java).toList()

        Assertions.assertEquals(
            response,
            FileHandler.getSurveys().find { it.name == "emergency" }!!.questions
        )
    }

    @Test
    fun testListSurveyQuestionsNotFound() {
        RestAssured.given()
            .`when`().get("/v1/surveys/nonExistingSurvey/questions")
            .then()
            .statusCode(404)
            .body(containsString("No survey found for nonExistingSurvey"))
    }

    @Test
    fun testListSurveys() {
        val response = RestAssured.given()
            .`when`().get("/v1/surveys")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(Array<Survey>::class.java)
        val actualSurveys = FileHandler.getSurveys()

        Assertions.assertEquals(response.size, actualSurveys.size)
        Assertions.assertEquals(response[0].name, actualSurveys[0].name)
        Assertions.assertEquals(response[1].name, actualSurveys[1].name)
        Assertions.assertEquals(response[2].name, actualSurveys[2].name)
        Assertions.assertEquals(response[0].questions, actualSurveys[0].questions)
        Assertions.assertEquals(response[1].questions, actualSurveys[1].questions)
        Assertions.assertEquals(response[2].questions, actualSurveys[2].questions)
    }
}