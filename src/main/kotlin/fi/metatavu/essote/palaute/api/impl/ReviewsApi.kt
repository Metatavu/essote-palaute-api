package fi.metatavu.essote.palaute.api.impl

import fi.metatavu.essote.palaute.api.controllers.ReviewsController
import fi.metatavu.model.ReviewListSort
import fi.metatavu.spec.ReviewsApi
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.core.Response

/**
 * API implementation for Reviews API
 */
@RequestScoped
class ReviewsApi: ReviewsApi, AbstractApi() {

    @Inject
    lateinit var reviewsController: ReviewsController

    override fun listReviews(
        productId: Int?,
        minRating: Int?,
        maxRating: Int?,
        minReviewLength: Int?,
        firstResult: Int?,
        maxResults: Int?,
        sort: ReviewListSort?
    ): Response {
        return try {
            val reviews = reviewsController.listReviews(
                productId = productId,
                minRating = minRating,
                maxRating = maxRating,
                minReviewLength = minReviewLength,
                firstResult = firstResult,
                maxResults = maxResults,
                sort = sort
            ) ?: return createNotFound("No reviews found")

            createOk(reviews)
        } catch (e: Error) {
            createInternalServerError(e.localizedMessage)
        }
    }
}