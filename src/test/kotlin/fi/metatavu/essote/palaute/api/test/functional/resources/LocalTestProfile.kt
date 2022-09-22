package fi.metatavu.essote.palaute.api.test.functional.resources

import io.quarkus.test.junit.QuarkusTestProfile

/**
 * Local test profile
 */
class LocalTestProfile: QuarkusTestProfile {

    override fun getConfigOverrides(): Map<String, String> {
        return mapOf(
            "products.file" to "src/main/resources/products.json",
            "surveys.file" to "src/main/resources/surveys.json"
        )
    }
}