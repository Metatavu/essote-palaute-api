package fi.metatavu.essote.palaute.api.bisnode.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.quarkus.runtime.annotations.RegisterForReflection
import java.time.OffsetDateTime

/**
 * POJO for storing reviews from Bisnode API
 */
@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
class BisnodeReview (
    val id: Int,
    val rating: Int,
    val created: OffsetDateTime
)