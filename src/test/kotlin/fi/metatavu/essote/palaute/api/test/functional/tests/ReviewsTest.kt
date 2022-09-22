package fi.metatavu.essote.palaute.api.test.functional.tests

import fi.metatavu.essote.palaute.api.test.functional.data.TestReviewsData
import fi.metatavu.essote.palaute.api.test.functional.resources.LocalTestProfile
import fi.metatavu.essote.palaute.api.test.functional.resources.TestWiremockResource
import fi.metatavu.model.Review
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.TestProfile
import io.restassured.RestAssured
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * Tests for Reviews API
 */
@QuarkusTest
@QuarkusTestResource(TestWiremockResource::class)
@TestProfile(LocalTestProfile::class)
class ReviewsTest {

    @Test
    fun testListReviewsWithoutParameters() {
        val response = RestAssured.given()
            .`when`().get("/v1/reviews")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(Array<Review>::class.java)
        val expectedReviewsProductFour = TestReviewsData.listMockReviewsOne()
        val expectedReviewsProductFive = TestReviewsData.listMockReviewsTwo()
        val actualReviewsProductFour = response.filter { it.productId == 4 }
        val actualReviewsProductFive = response.filter { it.productId == 5 }

        actualReviewsProductFour.forEachIndexed { index, review ->
            Assertions.assertEquals(review.rating, expectedReviewsProductFour[index].rating)
        }
        actualReviewsProductFive.forEachIndexed { index, review ->
            Assertions.assertEquals(review.rating, expectedReviewsProductFive[index].rating)
        }
    }

    @Test
    fun testListReviewsWithParameters() {
        val responseProductId4 = RestAssured.given()
            .`when`().get("/v1/reviews?productId=4")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(Array<Review>::class.java)
        val responseProductId5 = RestAssured.given()
            .`when`().get("/v1/reviews?productId=5")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(Array<Review>::class.java)
        val responseMinRating2 = RestAssured.given()
            .`when`().get("/v1/reviews?minRating=2")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(Array<Review>::class.java)
        val responseMaxRating3 = RestAssured.given()
            .`when`().get("/v1/reviews?maxRating=3")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(Array<Review>::class.java)
        val responseFirstResult5 = RestAssured.given()
            .`when`().get("/v1/reviews?productId=5&firstResult=5")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(Array<Review>::class.java)
        val responseMaxResult5 = RestAssured.given()
            .`when`().get("/v1/reviews?maxResults=2")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(Array<Review>::class.java)
        val allReviewsAsc = RestAssured.given()
            .`when`().get("/v1/reviews?sort=ASC")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(Array<Review>::class.java)
        val allReviewsDesc = RestAssured.given()
            .`when`().get("/v1/reviews")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(Array<Review>::class.java)

        responseProductId4.forEach {
            Assertions.assertTrue(it.productId == 4)
        }
        responseProductId5.forEach {
            Assertions.assertTrue(it.productId == 5)
        }
        responseMinRating2.forEach {
            Assertions.assertTrue(it.rating!! >= 2)
        }
        responseMaxRating3.forEach {
            Assertions.assertTrue(it.rating!! <= 3)
        }
        Assertions.assertTrue(responseFirstResult5.size == 7)
        Assertions.assertTrue(responseMaxResult5.size == 2)
        Assertions.assertTrue(allReviewsAsc.size == allReviewsDesc.size)
        allReviewsAsc.reverse()
        allReviewsAsc.forEachIndexed { index, review ->
            Assertions.assertEquals(review.rating, allReviewsDesc[index].rating)
            Assertions.assertEquals(review.productId, allReviewsDesc[index].productId)
        }
    }

    @Test
    fun testListReviewsNotFound() {
        RestAssured.given()
            .`when`().get("/v1/reviews?productId=0")
            .then()
            .statusCode(404)
            .body(containsString("No reviews found"))
    }
}