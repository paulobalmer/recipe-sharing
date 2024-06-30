package net.azeti.challenge.recipe.entrypoint.rest.dto.auth

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:NotNull(message = "Username is required")
    @field:Size(min = 5, max = 20, message = "Username must be between {min} and {max} characters")
    val username: String,

    @field:NotNull(message = "Password is required")
    @field:Size(min = 8, max = 35, message = "Password must be between {min} and {max} characters")
    val password: String
)