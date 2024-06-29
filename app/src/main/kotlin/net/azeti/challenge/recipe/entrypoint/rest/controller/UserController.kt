package net.azeti.challenge.recipe.entrypoint.rest.controller

import net.azeti.challenge.recipe.core.usecase.CreateUserUseCase
import net.azeti.challenge.recipe.entrypoint.rest.api.UserApi
import net.azeti.challenge.recipe.entrypoint.rest.dto.CreateUserRequest
import net.azeti.challenge.recipe.entrypoint.rest.mapper.UserDtoMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    val createUserUseCase: CreateUserUseCase,
    val mapper : UserDtoMapper
) : UserApi {
    override fun create(request: CreateUserRequest): ResponseEntity<Any> {
        val createdUser = createUserUseCase.execute(mapper.toDomain(request))
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toCreateUserResponseDto(createdUser))
    }


}