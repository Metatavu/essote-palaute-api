package fi.metatavu.essote.palaute.api.bisnode.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.quarkus.runtime.annotations.RegisterForReflection

/**
 * POJO for storing response from Bisnode's yes-no summary API
 */
@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
class BisnodeSurveySummary (

    val yes: Int? = null,

    val total: Int? = null
)