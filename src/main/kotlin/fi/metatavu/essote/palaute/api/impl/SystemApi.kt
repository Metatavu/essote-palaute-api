package fi.metatavu.essote.palaute.api.impl

import fi.metatavu.spec.SystemApi
import javax.ws.rs.core.Response

class SystemApi: SystemApi, AbstractApi() {
    override fun ping(): Response {
        return createOk("pong")
    }
}