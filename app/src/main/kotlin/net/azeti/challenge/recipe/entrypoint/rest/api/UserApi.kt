package net.azeti.challenge.recipe.entrypoint.rest.api

import jakarta.validation.Valid
import net.azeti.challenge.recipe.entrypoint.rest.dto.users.CreateUserRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping(path = ["/users"])
interface UserApi {

    @PostMapping
    fun create(@RequestBody @Valid request: CreateUserRequestDto): ResponseEntity<Any>

}