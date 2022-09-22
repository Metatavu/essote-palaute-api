package fi.metatavu.essote.palaute.api.controllers

import fi.metatavu.essote.palaute.api.bisnode.BisnodeService
import fi.metatavu.model.Review
import fi.metatavu.model.ReviewListSort
import org.slf4j.Logger
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

/**
 * Controller for Reviews
 */
@ApplicationScoped
class ReviewsController {

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var bisnodeService: BisnodeService

    @Inject
    lateinit var reviewProductsController: ReviewProductsController

    /**
     * Amount of delay between review products while performing scheduled action (in ms)
     */
    private val DELAY = 10000L

    /**
     * Finds review by id
     *
     * @param reviewId Long
     * @return Review or null
     */
    fun findReviewById(reviewId: Int): Review? {
        return null
    }

    /**
     * Lists reviews
     *
     * @param productId productId
     * @param maxRating max rating
     * @param minRating min rating
     * @param minReviewLength min review length
     * @param sort sort
     * @param firstResult first result
     * @param maxResults max results
     * @return List of Reviews
     */
    fun listReviews(
        productId: Int?,
        maxRating: Int?,
        minRating: Int?,
        minReviewLength: Int?,
        sort: ReviewListSort?,
        firstResult: Int?,
        maxResults: Int?
    ): List<Review>? {
        var reviews = mutableListOf<Review>()
        if (productId != null) {
            reviews.addAll(listReviewsByReviewProduct(
                reviewProductId = productId,
                firstResult = firstResult ?: 1,
                maxResults = maxResults ?: BisnodeService.PAGE_SIZE
            )!!)

            if (maxRating != null) {
                reviews = reviews.filter { it.rating!! <= maxRating }.toMutableList()
            }
            if (minRating != null) {
                reviews = reviews.filter { it.rating!! >= minRating }.toMutableList()
            }
            if (sort == ReviewListSort.ASC) {
                reviews.reverse()
            }

            return reviews
        }
//        val reviewProduct = reviewProductsController.findReviewProductById(
//            reviewProductId = 2
//        )
//        val reviews = bisnodeService.listReviews(
//            reviewProductName = reviewProduct!!.name!!,
//            page = 0L,
//            pagesToRetrieve = maxResults / BisnodeService.PAGE_SIZE
//        )

//        println("Reviews length: ${reviews.size}")

//        if (minReviewLength != null) {
//            reviews.forEach { println(it.review?.length) }
//            reviews = reviews.filter { it.review != null && it.review.length > minReviewLength }
//        }

        return null
    }

    /**
     * Gets reviews for single review product
     *
     * @param reviewProduct review product
     * @return List of Reviews or null
     */
    private fun listReviewsByReviewProduct(reviewProductId: Int, firstResult: Int, maxResults: Int): List<Review>? {
        val reviewProduct = reviewProductsController.findReviewProductById(reviewProductId)
            ?: return null

        return bisnodeService.listReviews(
            reviewProductName = reviewProduct.name!!
        ).filter { it.productId == reviewProductId }
    }

    /**
     * Gets review products from file, gets all reviews for them and caches them
     */
    fun cacheReviews() {
        reviewProductsController.listReviewProducts().forEach { reviewProduct ->
            logger.info("Getting reviews for ${reviewProduct.name}")
            bisnodeService.listReviews(
                reviewProductName = reviewProduct.name!!
            )
            logger.info("Waiting ${DELAY}ms before next requests")
            Thread.sleep(DELAY)
        }
    }
}