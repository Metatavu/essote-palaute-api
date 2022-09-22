package fi.metatavu.essote.palaute.api.impl

import fi.metatavu.essote.palaute.api.utils.JwtUtils
import fi.metatavu.spec.SystemApi
import javax.ws.rs.core.Response

class SystemApi: SystemApi, AbstractApi() {
    override fun ping(): Response {
        return createOk(JwtUtils.createToken())
    }
}