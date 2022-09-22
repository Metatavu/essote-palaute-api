package fi.metatavu.essote.palaute.api.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import fi.metatavu.essote.palaute.api.utils.FileUtils
import fi.metatavu.model.ReviewProduct
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.slf4j.Logger
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

/**
 * Controller for ReviewProducts
 */
@ApplicationScoped
class ReviewProductsController {

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var fileUtils: FileUtils

    @ConfigProperty(name = "products.file")
    private lateinit var productsFilePath: String

    /**
     * Finds a single review product by id
     *
     * @param reviewProductId review product id
     * @return ReviewProduct or null
     */
    fun findReviewProductById(reviewProductId: Int): ReviewProduct? {
        val reviewProducts = listReviewProducts()

        return reviewProducts.find { it.id == reviewProductId }
    }

    /**
     * Gets a list of review products
     *
     * @return Array of ReviewProducts
     */
    fun listReviewProducts(): Array<ReviewProduct> {
        return try {
            jacksonObjectMapper().readValue(fileUtils.readFromFile(productsFilePath), Array<ReviewProduct>::class.java)
        } catch (e: Error) {
            logger.error("Error while opening file $productsFilePath: ${e.localizedMessage}")
            throw e
        }
    }
}