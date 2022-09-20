package fi.metatavu.essote.palaute.api.test.functional.resources

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import fi.metatavu.essote.palaute.api.bisnode.model.BisnodeSurveySummary
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
     */
    private fun yesNoStubs(wiremockServer: WireMockServer) {
        wiremockServer.stubFor(
            get(urlPathEqualTo("/api/v$apiVersion/yes-no/emergency/1"))
                .willReturn(jsonResponse(objectMapper.writeValueAsString(createBisnodeSurveySummary(
                    yes = 92,
                    total = 100
                )), 200))
        )
    }

    override fun stop() {
        wiremockServer.stop()
    }

    /**
     * Creates BisnodeSurveySummary with given values
     *
     * @param yes yes
     * @param total total
     * @return BisnodeSurveySummary
     */
    private fun createBisnodeSurveySummary(yes: Long, total: Long): BisnodeSurveySummary {
        val newBisnodeSurveySummary = BisnodeSurveySummary()
        newBisnodeSurveySummary.yes = yes
        newBisnodeSurveySummary.total = total

        return newBisnodeSurveySummary
    }
}