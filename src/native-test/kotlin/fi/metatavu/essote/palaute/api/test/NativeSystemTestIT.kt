package fi.metatavu.essote.palaute.api.test

import fi.metatavu.essote.palaute.api.test.functional.resources.LocalTestProfile
import fi.metatavu.essote.palaute.api.test.functional.resources.TestWiremockResource
import fi.metatavu.essote.palaute.api.test.functional.tests.SurveysTest
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusIntegrationTest
import io.quarkus.test.junit.TestProfile

/**
 * Native tests for System API
 */
@QuarkusIntegrationTest
@QuarkusTestResource(TestWiremockResource::class)
@TestProfile(LocalTestProfile::class)
class NativeSystemTestIT: SurveysTest() {
}