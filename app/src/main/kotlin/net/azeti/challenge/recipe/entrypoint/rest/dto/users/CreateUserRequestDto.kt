package net.azeti.challenge.recipe.entrypoint.rest.dto.users

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreateUserRequestDto(

    @field:NotNull(message = "Name is required")
    @field:Size(min = 5, max = 100, message = "Name must be between {min} and {max} characters")
    @JsonProperty("name") val name: String,

    @field:NotNull(message = "Username is required")
    @field:Size(min = 5, max = 20, message = "Username must be between {min} and {max} characters")
    @JsonProperty("username") val username: String,

    @field:NotNull(message = "Email is required")
    @field:Email(message = "Provided email address is not valid")
    @JsonProperty("email") val email: String,

    @field:NotNull(message = "Password is required")
    @field:Size(min = 8, max = 35, message = "Password must be between {min} and {max} characters")
    @JsonProperty("password") val password: String
)