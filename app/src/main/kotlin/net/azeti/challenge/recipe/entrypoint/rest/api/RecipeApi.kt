package net.azeti.challenge.recipe.entrypoint.rest.api

import jakarta.validation.Valid
import net.azeti.challenge.recipe.entrypoint.rest.dto.recipes.CreateRecipeRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@RequestMapping(path = ["/recipes"])
interface RecipeApi {

    @Secured("USER")
    @PostMapping
    fun create(@RequestBody @Valid request: CreateRecipeRequestDto, authentication: Authentication): ResponseEntity<Any>

    @Secured("USER")
    @GetMapping(value = ["/{id}"])
    fun getById(@PathVariable id : UUID): ResponseEntity<Any>

}