package fi.metatavu.essote.palaute.api.bisnode.model

import fi.metatavu.model.Review
import io.quarkus.runtime.annotations.RegisterForReflection

/**
 * POJO for storing response from Bisnode's reviews API
 */
@RegisterForReflection
class BisnodeReviewResponse {

    val total: Int? = null

    val reviews: List<Review>? = null
}