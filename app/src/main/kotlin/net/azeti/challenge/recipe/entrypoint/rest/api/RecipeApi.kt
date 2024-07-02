package net.azeti.challenge.recipe.entrypoint.rest.api

import jakarta.validation.Valid
import net.azeti.challenge.recipe.entrypoint.rest.dto.recipes.CreateRecipeRequestDto
import net.azeti.challenge.recipe.entrypoint.rest.dto.recipes.RecipeDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping(path = ["/recipes"])
interface RecipeApi {

    @Secured("USER")
    @PostMapping
    fun create(@RequestBody @Valid request: CreateRecipeRequestDto, authentication: Authentication): ResponseEntity<Any>

    @Secured("USER")
    @GetMapping
    fun listAll(@RequestParam(required = false) username: String?,
                @RequestParam(required = false) title: String?,
                pageable: Pageable
    ): ResponseEntity<Page<RecipeDto>>

    @Secured("USER")
    @PutMapping(value = ["/{id}"])
    fun update(@PathVariable id: UUID, @RequestBody @Valid request: CreateRecipeRequestDto, authentication: Authentication): ResponseEntity<Any>

    @Secured("USER")
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: UUID, authentication: Authentication): ResponseEntity<Any>

    @Secured("USER")
    @GetMapping(value = ["/{id}"])
    fun getById(@PathVariable id : UUID): ResponseEntity<Any>

}