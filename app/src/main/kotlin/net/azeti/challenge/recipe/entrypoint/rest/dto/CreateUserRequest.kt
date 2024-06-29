package net.azeti.challenge.recipe.entrypoint.rest.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateUserRequest(
    @JsonProperty("username") val username: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("password") val password: String
)