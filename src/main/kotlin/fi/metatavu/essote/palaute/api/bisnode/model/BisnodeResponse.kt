package fi.metatavu.essote.palaute.api.bisnode.model

import io.quarkus.runtime.annotations.RegisterForReflection

/**
 * POJO for storing response from Bisnode HTTP requests
 */
@RegisterForReflection
class BisnodeResponse (

    val totalCount: Int? = null,

    val content: String? = null
)