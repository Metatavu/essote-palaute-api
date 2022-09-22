package fi.metatavu.essote.palaute.api.impl

import fi.metatavu.essote.palaute.api.controllers.ReviewProductsController
import fi.metatavu.spec.ReviewProductsApi
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.core.Response

/**
 * API implementation for ReviewProducts API
 */
@RequestScoped
class ReviewProductsApi: ReviewProductsApi, AbstractApi() {

    @Inject
    lateinit var reviewProductsController: ReviewProductsController

    override fun findReviewProduct(reviewProductId: Int): Response {
        return try {
            val reviewProduct = reviewProductsController.findReviewProductById(reviewProductId)
                ?: return createNotFound("No ReviewProduct found for $reviewProductId")

            createOk(reviewProduct)
        } catch (e: Error) {
            createInternalServerError(e.localizedMessage)
        }
    }

    override fun listReviewProducts(): Response {
        return try {
            createOk(reviewProductsController.listReviewProducts())
        } catch (e: Error) {
            createInternalServerError(e.localizedMessage)
        }
    }
}