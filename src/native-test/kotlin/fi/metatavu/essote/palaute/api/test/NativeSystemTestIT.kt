package fi.metatavu.essote.palaute.api.test

import fi.metatavu.essote.palaute.api.test.functional.resources.TestWiremockResource
import fi.metatavu.essote.palaute.api.test.functional.tests.SurveysTest
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusIntegrationTest

/**
 * Native tests for System API
 */
@QuarkusIntegrationTest
@QuarkusTestResource(TestWiremockResource::class)
class NativeSystemTestIT: SurveysTest() {
}