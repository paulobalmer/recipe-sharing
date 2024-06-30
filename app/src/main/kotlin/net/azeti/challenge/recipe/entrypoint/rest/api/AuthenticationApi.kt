package net.azeti.challenge.recipe.entrypoint.rest.api

import jakarta.validation.Valid
import net.azeti.challenge.recipe.entrypoint.rest.dto.auth.LoginRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping(path = ["/auth"])
interface AuthenticationApi {

    @PostMapping(value = ["/token"])
    fun login(@RequestBody @Valid request: LoginRequest): ResponseEntity<*>

}