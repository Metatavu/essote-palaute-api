package fi.metatavu.essote.palaute.api.bisnode

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import fi.metatavu.essote.palaute.api.utils.JwtUtils
import fi.metatavu.model.Review
import fi.metatavu.model.SurveyQuestionSummary
import okhttp3.OkHttpClient
import okhttp3.Request
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.slf4j.Logger
import javax.enterprise.context.RequestScoped
import javax.inject.Inject

/**
 * Service for accessing Bisnode API
 */
@RequestScoped
class BisnodeService {

//    @Inject
//    lateinit var logger: Logger

    @ConfigProperty(name = "bisnode.base.url")
    lateinit var bisnodeBaseUrl: String

    @ConfigProperty(name = "bisnode.api.version")
    lateinit var bisnodeApiVersion: String

    private val pageSize = 10
    private val reviewSyncEnabled = "bisnode.review-sync.enabled"

    /**
     * Gets survey question summary from Bisnode API
     *
     * @param surveyQuestion survey question to get the summary for
     * @return Summary for provided survey question or null if not available
     */
    fun getSurveyQuestionSummary(surveyName: String, questionNumber: Int): SurveyQuestionSummary {
        return try {
            val path = "v$bisnodeApiVersion/yes-no/$surveyName/$questionNumber"
            jacksonObjectMapper().readValue(doRequest(path), SurveyQuestionSummary::class.java)
        } catch (e: Error) {
            throw e
        }
    }

    /**
     * Lists reviews from Bisnode API
     *
     * @param reviewProductName review product name to find the reviews for
     * @param page page number
     * @return List of Reviews
     */
    fun listReviews(reviewProductName: String, page: Int): List<Review> {
        return try {
            val path = "v$bisnodeApiVersion/reviews/$reviewProductName?page=$page&size=$pageSize"
            jacksonObjectMapper().readValue(doRequest(path), Array<Review>::class.java).toList()
        } catch (e: Error) {
            throw e
        }
    }

    /**
     * Does HTTP GET-request to Bisnode API
     *
     * @param path
     * @return Response string
     */
    private fun doRequest(path: String): String? {
        return try {
            val client = OkHttpClient()
            val request = Request.Builder().url("$bisnodeBaseUrl/$path")
                .addHeader("Authorization", "Bearer ${JwtUtils.createToken()}")
                .build()
            val response = client.newCall(request).execute()
            when (response.code()) {
                200 -> response.body()?.toString()
                else -> throw Error(response.message())
            }
        } catch (e: Error) {
//            logger.error("Error when communicating with Bisnode: $e")
            throw e
        }
    }
}