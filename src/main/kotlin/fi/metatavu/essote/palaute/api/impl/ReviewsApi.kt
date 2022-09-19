package fi.metatavu.essote.palaute.api.impl

import fi.metatavu.model.ReviewListSort
import fi.metatavu.spec.ReviewsApi
import javax.ws.rs.core.Response

/**
 * API implementation for Reviews API
 */
class ReviewsApi: ReviewsApi, AbstractApi() {
    override suspend fun findReview(reviewId: Long): Response {
        TODO("Not yet implemented")
    }

    override suspend fun listReviews(
        productId: Long?,
        minRating: Int?,
        maxRating: Int?,
        minReviewLength: Int?,
        firstResult: Long?,
        maxResults: Long?,
        sort: ReviewListSort?
    ): Response {
        TODO("Not yet implemented")
    }
}