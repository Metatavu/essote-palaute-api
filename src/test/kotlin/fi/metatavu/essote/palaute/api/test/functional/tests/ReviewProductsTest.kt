package fi.metatavu.essote.palaute.api.test.functional.tests

import fi.metatavu.essote.palaute.api.test.functional.data.FileHandler
import fi.metatavu.essote.palaute.api.test.functional.resources.LocalTestProfile
import fi.metatavu.essote.palaute.api.test.functional.resources.TestWiremockResource
import fi.metatavu.model.ReviewProduct
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.TestProfile
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.Test

/**
 * Tests for ReviewProducts API
 */
@QuarkusTest
@QuarkusTestResource(TestWiremockResource::class)
@TestProfile(LocalTestProfile::class)
class ReviewProductsTest {

    @Test
    fun testFindReviewProduct() {
        val expectedReviewProduct = FileHandler.getReviewProducts().find{ it.name == "testProductOne" }
        val response = RestAssured.given()
            .`when`().get("/v1/reviewProducts/1")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(ReviewProduct::class.java)

        Assertions.assertEquals(response, expectedReviewProduct)
    }

    @Test
    fun testFindReviewProductNotFound() {
        RestAssured.given()
            .`when`().get("/v1/reviewProducts/0")
            .then()
            .statusCode(404)
            .body(containsString("No ReviewProduct found for 0"))
    }

    @Test
    fun testListReviewProducts() {
        val response = RestAssured.given()
            .`when`().get("/v1/reviewProducts")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .`as`(Array<ReviewProduct>::class.java).sortedBy { it.id }
        val actualReviewProducts = FileHandler.getReviewProducts().sortedBy { it.id }
        
        Assertions.assertEquals(response.size, actualReviewProducts.size)
        response.forEachIndexed { index, reviewProduct ->
            Assertions.assertEquals(reviewProduct.id, actualReviewProducts[index].id)
            Assertions.assertEquals(reviewProduct.name, actualReviewProducts[index].name)
            Assertions.assertEquals(reviewProduct.displayName, actualReviewProducts[index].displayName)
            Assertions.assertEquals(reviewProduct.link, actualReviewProducts[index].link)
        }
    }
}