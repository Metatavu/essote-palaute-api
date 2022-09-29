package fi.metatavu.essote.palaute.api.test.functional.resources

import io.quarkus.test.junit.QuarkusTestProfile

/**
 * Local test profile
 */
class LocalTestProfile: QuarkusTestProfile {

    override fun getConfigOverrides(): Map<String, String> {
        return mapOf(
            "products.file" to "src/test/resources/products.json",
            "surveys.file" to "src/test/resources/surveys.json",
            "scheduled.enabled" to "false"
        )
    }
}