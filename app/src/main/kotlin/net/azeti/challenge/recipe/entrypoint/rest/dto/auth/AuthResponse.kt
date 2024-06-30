package net.azeti.challenge.recipe.entrypoint.rest.dto.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthResponse(
    @JsonProperty("username") val username: String,
    @JsonProperty("accessToken") val accessToken: String,
    @JsonProperty("expires_in") val expiresIn: Long)