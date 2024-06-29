package net.azeti.challenge.recipe.entrypoint.rest.api

import jakarta.validation.Valid
import net.azeti.challenge.recipe.entrypoint.rest.dto.LoginRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping(path = ["/users"])
interface UserApi {

    @Secured("USER")
    @PostMapping
    fun create(@RequestBody @Valid request: LoginRequest): ResponseEntity<Any>

}