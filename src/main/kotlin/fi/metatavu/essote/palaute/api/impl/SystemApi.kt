package fi.metatavu.essote.palaute.api.impl

import fi.metatavu.spec.SystemApi
import javax.enterprise.context.RequestScoped
import javax.ws.rs.core.Response

/**
 * API implementation for System API
 */
@RequestScoped
class SystemApi: SystemApi, AbstractApi() {
    override fun ping(): Response {
        return createOk("pong")
    }
}