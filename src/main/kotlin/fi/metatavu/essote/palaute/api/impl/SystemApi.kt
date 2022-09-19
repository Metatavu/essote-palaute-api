package fi.metatavu.essote.palaute.api.impl

import fi.metatavu.spec.SystemApi
import javax.ws.rs.core.Response

class SystemApi: SystemApi, AbstractApi() {
    override suspend fun ping(): Response {
        return createOk("pong")
    }
}