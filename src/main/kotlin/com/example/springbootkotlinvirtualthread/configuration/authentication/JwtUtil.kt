package com.example.springbootkotlinvirtualthread.configuration.authentication

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil(
    private val jwtConfigProperties: JwtConfigProperties
) {

    fun parseJwtClaims(token: String): Claims = Jwts.parser()
        .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfigProperties.secretKey)))
        .build()
        .parseSignedClaims(token)
        .payload

    fun generateJwt(memberId: Long, tokenType: AuthenticationTokenType): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        if (tokenType == AuthenticationTokenType.ACCESS) {
            calendar.add(Calendar.SECOND, jwtConfigProperties.accessTokenValidityInSeconds)
        } else {
            calendar.add(Calendar.SECOND, jwtConfigProperties.refreshTokenValidityInSeconds)
        }
        val expiration = calendar.time
        return Jwts.builder()
            .header().type("JWT")
            .and()
            .expiration(expiration)
            .issuedAt(Date())
            .id(UUID.randomUUID().toString())
            .claims(
                mapOf(
                    AuthenticationToken.MEMBER_ID_CLAIM_KEY to memberId,
                    AuthenticationToken.TOKEN_TYPE_CLAIM_KEY to tokenType
                )
            )
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfigProperties.secretKey)))
            .compact()
    }
}
