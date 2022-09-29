package fi.metatavu.essote.palaute.api.impl

import javax.ws.rs.core.Response

/**
 * Abstract base class for all API services
 */
abstract class AbstractApi {

    /**
     * Constructs OK (200) response
     *
     * @param entity entity payload
     * @return response
     */
    protected fun createOk(entity: Any): Response {
        return Response
            .status(Response.Status.OK)
            .entity(entity)
            .build()
    }

    /**
     * Constructs OK (200) response
     * with total-hits and total-unfiltered-hits headers
     *
     * @param entity entity payload
     * @param totalHits total-hits header value
     * @param totalUnfilteredHits total-unfiltered-hits header value
     * @return response
     */
    protected fun createOk(
        entity: Any,
        totalHits: Int,
        totalUnfilteredHits: Int
    ): Response {
        return Response
            .status(Response.Status.OK)
            .entity(entity)
            .header("total-hits", totalHits)
            .header("total-unfiltered-hits", totalUnfilteredHits)
            .build()
    }

    /**
     * Constructs not found (404) response
     *
     * @param message message
     * @return response
     */
    protected fun createNotFound(message: String): Response {
        return createError(Response.Status.NOT_FOUND, message)
    }

    /**
     * Constructs internal server error (500) response
     *
     * @param message message
     * @return response
     */
    protected fun createInternalServerError(message: String): Response {
        return createError(Response.Status.INTERNAL_SERVER_ERROR, message)
    }

    /**
     * Constructs an error response
     *
     * @param status status
     * @param message message
     * @return bad request response
     */
    private fun createError(status: Response.Status, message: String): Response {
        val entity = fi.metatavu.model.Error(
            code = status.statusCode,
            message = message
        )

        return Response
            .status(status)
            .entity(entity)
            .build()
    }
}