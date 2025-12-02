package com.example.springbootkotlinvirtualthread.configuration.security

import com.example.springbootkotlinvirtualthread.configuration.authentication.AuthenticationToken
import com.example.springbootkotlinvirtualthread.configuration.authentication.AuthenticationTokenManager
import com.example.springbootkotlinvirtualthread.configuration.authentication.AuthenticationTokenType
import com.example.springbootkotlinvirtualthread.configuration.logger.logger
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val authenticationTokenManager: AuthenticationTokenManager
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt = AuthenticationToken.getJwtFromRequest(request = request)

            if (jwt != null &&
                authenticationTokenManager.isSavedToken(jwt) &&
                authenticationTokenManager.validateToken(token = jwt, tokenType = AuthenticationTokenType.ACCESS))
            {
                val accountId = authenticationTokenManager.getAccountIdFromToken(token = jwt)
                val authentication = UsernamePasswordAuthenticationToken(
                    accountId,
                    null,
                    emptyList()
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            logger().error("Could not set user authentication in security context", e)
            // 예외를 다시 던지지 않고 SecurityContext에 인증 정보를 설정하지 않음.
            // Spring Security가 인증되지 않은 요청으로 처리하고 AuthenticationEntryPoint를 호출함.
        }

        filterChain.doFilter(request, response)
    }
}