package net.azeti.challenge.recipe.entrypoint.rest.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import net.azeti.challenge.recipe.entrypoint.rest.dto.errors.StandardError
import net.azeti.challenge.recipe.entrypoint.rest.dto.errors.ValidationError
import net.azeti.challenge.recipe.entrypoint.rest.dto.users.CreateUserRequestDto
import net.azeti.challenge.recipe.entrypoint.rest.dto.users.CreateUserResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping(path = ["/users"])
interface UserApi {

    @Operation(
        description = "This operation creates a user account in the system.",
        security = []
    )
    @ApiResponse(
        responseCode = "201",
        description = "User account was created successfully",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = CreateUserResponseDto::class))]
    )
    @ApiResponse(
        responseCode = "400",
        description = "Same mandatory field is missing or null",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ValidationError::class))]
    )
    @ApiResponse(
        responseCode = "422",
        description = "User with same username or email address already exists",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = StandardError::class))]
    )
    @PostMapping
    fun create(@RequestBody @Valid request: CreateUserRequestDto): ResponseEntity<Any>

}