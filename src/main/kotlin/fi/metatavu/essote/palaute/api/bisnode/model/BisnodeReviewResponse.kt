package fi.metatavu.essote.palaute.api.bisnode.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import fi.metatavu.model.Review
import io.quarkus.runtime.annotations.RegisterForReflection

/**
 * POJO for storing response from Bisnode's reviews API
 */
@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
class BisnodeReviewResponse {

    val total: Int? = null

    val reviews: List<Review>? = null
}