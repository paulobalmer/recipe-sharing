package net.azeti.challenge.recipe.entrypoint.rest.controller

import net.azeti.challenge.recipe.entrypoint.rest.api.UserApi
import net.azeti.challenge.recipe.entrypoint.rest.dto.LoginRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController : UserApi {
    override fun create(request: LoginRequest): ResponseEntity<Any> {
        return ResponseEntity.ok().build()
    }


}