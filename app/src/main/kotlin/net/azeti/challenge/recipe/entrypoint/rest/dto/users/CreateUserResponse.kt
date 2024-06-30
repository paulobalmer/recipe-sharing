package net.azeti.challenge.recipe.entrypoint.rest.dto.users

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class CreateUserResponse(
    @JsonProperty("id") val id: UUID,
    @JsonProperty("username") val username: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("active") val active: Boolean
)