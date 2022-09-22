package fi.metatavu.essote.palaute.api.test.functional.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import fi.metatavu.essote.palaute.api.bisnode.model.BisnodeSurveySummary
import fi.metatavu.essote.palaute.api.test.functional.data.TestReviewsData
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager

/**
 * Wiremock to mock Bisnode API
 */
class TestWiremockResource: QuarkusTestResourceLifecycleManager {

    private val wiremockServer = WireMockServer(8082)
    private val objectMapper = ObjectMapper()
    private val apiVersion = "1"

    override fun start(): Map<String, String> {
        objectMapper.findAndRegisterModules()
        wiremockServer.start()
        configureFor("localhost", 8082)

        yesNoStubs(wiremockServer)
        reviewsStubs(wiremockServer)

        return mapOf(
            "bisnode.base.url" to "${wiremockServer.baseUrl()}/api/",
            "bisnode.secret" to "secretKey",
            "bisnode.issuer" to "text.example.com",
            "bisnode.audience" to "test.example.com",
            "bisnode.api.version" to apiVersion
        )
    }

    /**
     * Stubs for Bisnode API yes-no endpoints
     *
     * @param wiremockServer WireMockServer
     */
    private fun yesNoStubs(wiremockServer: WireMockServer) {
        wiremockServer.stubFor(
            get(urlPathEqualTo("/api/v$apiVersion/yes-no/testSurveyTwo/1"))
                .willReturn(jsonResponse(objectMapper.writeValueAsString(BisnodeSurveySummary(
                    yes = 92,
                    total = 100
                )), 200))
        )
    }

    /**
     * Stubs for Bisnode API reviews endpoints
     *
     * @param wiremockServer WireMockServer
     */
    private fun reviewsStubs(wiremockServer: WireMockServer) {
        wiremockServer.stubFor(
            get(urlPathEqualTo("/api/v$apiVersion/reviews/testProductFour"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("20"))
                .willReturn(
                    jsonResponse(objectMapper.writeValueAsString(TestReviewsData.listMockReviewsOne()), 200)
                    .withHeader("x-total-count", TestReviewsData.listMockReviewsOne().size.toString())
                )
        )
        wiremockServer.stubFor(
            get(urlPathEqualTo("/api/v$apiVersion/reviews/testProductFive"))
                .withQueryParam("page", equalTo("0"))
                .withQueryParam("size", equalTo("20"))
                .willReturn(
                    jsonResponse(objectMapper.writeValueAsString(TestReviewsData.listMockReviewsTwo()), 200)
                        .withHeader("x-total-count", TestReviewsData.listMockReviewsTwo().size.toString())
                )
        )
        wiremockServer.stubFor(
            get(urlPathEqualTo("/api/v$apiVersion/reviews/nonExistingTestProduct?page=0&size=20"))
                .willReturn(badRequest())
        )
    }

    override fun stop() {
        wiremockServer.stop()
    }
}