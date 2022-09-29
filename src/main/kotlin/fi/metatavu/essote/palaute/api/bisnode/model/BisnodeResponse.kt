package fi.metatavu.essote.palaute.api.bisnode.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import io.quarkus.runtime.annotations.RegisterForReflection

/**
 * POJO for storing response from Bisnode HTTP requests
 */
@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
class BisnodeResponse (

    val totalCount: Int? = null,

    val content: String? = null
)