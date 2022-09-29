package fi.metatavu.essote.palaute.api.bisnode

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import fi.metatavu.essote.palaute.api.bisnode.model.BisnodeResponse
import fi.metatavu.essote.palaute.api.bisnode.model.BisnodeReview
import fi.metatavu.essote.palaute.api.bisnode.model.BisnodeSurveySummary
import fi.metatavu.essote.palaute.api.utils.JwtUtils
import fi.metatavu.model.Review
import fi.metatavu.model.SurveyQuestionSummary
import io.quarkus.cache.CacheResult
import okhttp3.OkHttpClient
import okhttp3.Request
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.slf4j.Logger
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

/**
 * Service for accessing Bisnode API
 */
@ApplicationScoped
class BisnodeService {

    @Inject
    lateinit var logger: Logger

    @ConfigProperty(name = "bisnode.base.url")
    lateinit var bisnodeBaseUrl: String

    @Inject
    @ConfigProperty(name = "bisnode.api.version")
    lateinit var bisnodeApiVersion: String

    private val objectMapper: ObjectMapper
        get() {
            return jacksonObjectMapper()
                .findAndRegisterModules()
                .registerModule(JavaTimeModule())
        }

    /**
     * Gets survey question summary from Bisnode API
     *
     * @param surveyName survey name
     * @param questionNumber question number
     * @return Summary for provided survey question or null if not available
     */
    fun getSurveyQuestionSummary(surveyName: String, questionNumber: Int): SurveyQuestionSummary {
        val path = "v$bisnodeApiVersion/yes-no/$surveyName/$questionNumber"
        val response = objectMapper.readValue<BisnodeSurveySummary>(doRequest(path).content!!)

        return SurveyQuestionSummary(
            positive = response.yes,
            negative = response.total?.minus(response.yes!!),
            total = response.total
        )
    }

    /**
     * Lists reviews from Bisnode API
     *
     * @param reviewProductName review product name
     * @param reviewProductId review product id
     * @return List of Reviews
     */
    @CacheResult(cacheName = "reviews-cache")
    fun listReviews(reviewProductName: String, reviewProductId: Int): List<Review> {
        return retrieveAllReviews(
            reviewProductName = reviewProductName,
            reviewProductId = reviewProductId
        )
    }

    /**
     * Handles paginated HTTP responses
     *
     * @param reviewProductName review product name
     * @param reviewProductId review product id
     * @return List of Reviews
     */
    private fun retrieveAllReviews(reviewProductName: String, reviewProductId: Int): List<Review> {
        var retrievedAllReviews = false
        val reviews = mutableListOf<Review>()
        var pagesRetrieved = 0
        var pageNumber = 0

        while (!retrievedAllReviews) {
            try {
                val path = "v$bisnodeApiVersion/reviews/$reviewProductName?page=$pageNumber&size=$PAGE_SIZE"
                val bisnodeResponse = doRequest(path)

                pageNumber++

                if (pageNumber * PAGE_SIZE > bisnodeResponse.totalCount!!) {
                    retrievedAllReviews = true
                }

                pagesRetrieved++
                reviews.addAll(
                    objectMapper.readValue<List<BisnodeReview>>(bisnodeResponse.content!!).toMutableList().map { bisnodeReview ->
                        Review(
                            id = bisnodeReview.id,
                            productId = reviewProductId,
                            rating = bisnodeReview.rating,
                            created = bisnodeReview.created
                        )
                    }
                )
            } catch (e: Error) {
                logger.error("Error while retrieving reviews for product: $reviewProductName", e)
                throw e
            }
        }

        return reviews
    }

    /**
     * Does HTTP GET-request to Bisnode API
     *
     * @param path path
     * @return Response string
     */
    private fun doRequest(path: String): BisnodeResponse {
        return try {
            val client = OkHttpClient()
            val request = Request.Builder().url("$bisnodeBaseUrl$path")
                .addHeader("Authorization", "Bearer ${JwtUtils.createToken()}")
                .build()
            val response = client.newCall(request).execute()

            when (response.code()) {
                200 -> {
                    BisnodeResponse(
                        totalCount = response.header("x-total-count")?.toInt(),
                        content = response.body()?.string()
                    )
                }
                else -> throw Error(response.toString())
            }
        } catch (e: Error) {
            logger.error("Error when communicating with Bisnode: ${e.localizedMessage}")
            throw e
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}