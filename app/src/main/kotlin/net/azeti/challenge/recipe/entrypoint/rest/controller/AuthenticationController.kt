package net.azeti.challenge.recipe.entrypoint.rest.controller

import net.azeti.challenge.recipe.core.security.CustomUserDetails
import net.azeti.challenge.recipe.core.security.jwt.JwtUtil
import net.azeti.challenge.recipe.entrypoint.rest.api.AuthenticationApi
import net.azeti.challenge.recipe.entrypoint.rest.dto.auth.AuthResponse
import net.azeti.challenge.recipe.entrypoint.rest.dto.auth.LoginRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(
    private val jwtUtil: JwtUtil,
    private val authManager: AuthenticationManager
) : AuthenticationApi {

    override fun login(request: LoginRequest): ResponseEntity<*> {
        return try {
            val authentication: Authentication = authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    request.username,
                    request.password
                )
            )

            val userDetails: CustomUserDetails = authentication.principal as CustomUserDetails
            val tokenResponse: AuthResponse = jwtUtil.generateToken(userDetails.username)

            ResponseEntity.ok().body(tokenResponse)
        } catch (ex: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build<Any>()
        }
    }

}