package fi.metatavu.essote.palaute.api.bisnode.model

import io.quarkus.runtime.annotations.RegisterForReflection

/**
 * POJO for storing response from Bisnode's yes-no summary API
 */
@RegisterForReflection
class BisnodeSurveySummary (

    val yes: Int? = null,

    val total: Int? = null
)