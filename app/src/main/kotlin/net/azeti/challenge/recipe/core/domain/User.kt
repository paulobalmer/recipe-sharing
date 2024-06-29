package net.azeti.challenge.recipe.core.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class User(
    @JsonProperty("id") val id: UUID,
    @JsonProperty("username") val username: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("password") val password: String,
    @JsonProperty("active") val active: Boolean?
)