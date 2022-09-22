package fi.metatavu.essote.palaute.api.test.functional.tests

import fi.metatavu.essote.palaute.api.test.functional.resources.LocalTestProfile
import fi.metatavu.essote.palaute.api.test.functional.resources.TestWiremockResource
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.TestProfile
import org.junit.jupiter.api.Test
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`

/**
 * Tests for System API
 */
@QuarkusTest
@QuarkusTestResource(TestWiremockResource::class)
@TestProfile(LocalTestProfile::class)
class SystemTest {

    @Test
    fun testPingEndpoint() {
        given()
            .`when`().get("/v1/system/ping")
            .then()
            .statusCode(200)
            .body(`is`("pong"))
    }
}