package net.azeti.challenge.recipe.entrypoint.rest.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import net.azeti.challenge.recipe.entrypoint.rest.dto.auth.AuthResponse
import net.azeti.challenge.recipe.entrypoint.rest.dto.auth.LoginRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping(path = ["/auth"])
interface AuthenticationApi {


    @Operation(
        description = "Operation to perform login and get a access token in platform.",
        security = []
    )
    @ApiResponse(
        responseCode = "200",
        description = "User account was created successfully",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = AuthResponse::class))]
    )
    @ApiResponse(
        responseCode = "401",
        description = "Access denied in the platform",
        content = [Content(mediaType = "application/json")]
    )
    @PostMapping(value = ["/token"])
    fun login(@RequestBody @Valid request: LoginRequest): ResponseEntity<*>

}