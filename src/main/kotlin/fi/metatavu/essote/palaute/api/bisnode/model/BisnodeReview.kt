package fi.metatavu.essote.palaute.api.bisnode.model

import io.quarkus.runtime.annotations.RegisterForReflection
import java.time.OffsetDateTime

@RegisterForReflection
class BisnodeReview (
    val id: Int,
    val rating: Int,
    val created: OffsetDateTime
)