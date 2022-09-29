package fi.metatavu.essote.palaute.api.utils

import org.eclipse.microprofile.config.ConfigProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.apache.commons.text.StringEscapeUtils
import java.time.OffsetDateTime
import java.util.*

/**
 * Helper class for creating JWT tokens
 */
class JwtUtils {

    companion object {

        /**
         * Creates signed JWT token
         *
         * @return JWT token string
         */
        fun createToken(): String {
            val tokenSecret = StringEscapeUtils.unescapeHtml4(ConfigProvider.getConfig().getValue("bisnode.secret", String::class.java))
            val issuer = ConfigProvider.getConfig().getValue("bisnode.issuer", String::class.java)
            val audience = ConfigProvider.getConfig().getValue("bisnode.audience", String::class.java)
            val iat = OffsetDateTime.now()
            val nbf = iat.minusSeconds(5)
            val exp = iat.plusSeconds(60)
            val algorithm = Algorithm.HMAC256(tokenSecret)

            return JWT.create()
                .withIssuer(issuer)
                .withAudience(audience)
                .withNotBefore(Date.from(nbf.toInstant()))
                .withIssuedAt(Date.from(iat.toInstant()))
                .withExpiresAt(Date.from(exp.toInstant()))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm)
        }
    }
}