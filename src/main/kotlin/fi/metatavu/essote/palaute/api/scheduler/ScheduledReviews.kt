package fi.metatavu.essote.palaute.api.scheduler

import fi.metatavu.essote.palaute.api.controllers.ReviewsController
import io.quarkus.scheduler.Scheduled
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.slf4j.Logger
import java.time.Duration
import java.time.OffsetDateTime
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

/**
 * Class for scheduled Review fetching from Bisnode API
 */
@ApplicationScoped
class ScheduledReviews {

    @Inject
    lateinit var reviewsController: ReviewsController

    @Inject
    lateinit var logger: Logger

    @Inject
    @ConfigProperty(name = "scheduled.enabled")
    var scheduledEnabled = false

    /**
     * Gets Reviews from Bisnode API on schedule
     */
    @Scheduled(every = "30m")
    fun scheduledReviews() {
        if (!scheduledEnabled) {
            return
        }

        val startTime = OffsetDateTime.now()
        logger.info("Starting to cache reviews from Bisnode API at $startTime")
        reviewsController.cacheReviews()
        val endTime = OffsetDateTime.now()
        val duration = Duration.between(startTime, endTime).toMinutes()
        logger.info("Finished caching reviews from Bisnode API at $endTime after ${duration}min")
    }
}