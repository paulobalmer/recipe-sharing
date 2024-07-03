package net.azeti.challenge.recipe.entrypoint.rest.api

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import net.azeti.challenge.recipe.entrypoint.rest.dto.errors.ValidationError
import net.azeti.challenge.recipe.entrypoint.rest.dto.recipes.CreateRecipeRequestDto
import net.azeti.challenge.recipe.entrypoint.rest.dto.recipes.RecipeDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.*

@OpenAPIDefinition(
    info = Info(
        title = "Recipes API",
        version = "1.0.0",
        description = "The Recipes API service provides mechanisms to manage and share Recipes\n"
    )
)
@RequestMapping(path = ["/recipes"])
interface RecipeApi {


    @Operation(
        description = "This operation creates a recipe in the system.",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @ApiResponse(
        responseCode = "201",
        description = "Recipe was created successfully",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = RecipeDto::class))]
    )
    @ApiResponse(
        responseCode = "400",
        description = "Same mandatory field is missing or null",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = ValidationError::class))]
    )
    @Secured("USER")
    @PostMapping
    fun create(@RequestBody @Valid request: CreateRecipeRequestDto, authentication: Authentication): ResponseEntity<Any>

    @Operation(
        description = "This operation searchs a recipe in the system.",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @ApiResponse(
        responseCode = "200",
        description = "Returns a list of recipes in the system",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = RecipePageDto::class))]
    )
    @Secured("USER")
    @GetMapping
    fun listAll(@RequestParam(required = false) username: String?,
                @RequestParam(required = false) title: String?,
                pageable: Pageable
    ): ResponseEntity<Page<RecipeDto>>

    @Operation(
        description = "This operation updates a specify recipe in the system.",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @ApiResponse(
        responseCode = "200",
        description = "Recipe was updated successfully",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = RecipeDto::class))]
    )
    @Secured("USER")
    @PutMapping(value = ["/{id}"])
    fun update(@PathVariable id: UUID, @RequestBody @Valid request: CreateRecipeRequestDto, authentication: Authentication): ResponseEntity<Any>

    @Operation(
        description = "This operation removes a specify recipe in the system.",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @ApiResponse(
        responseCode = "201",
        description = "Recipe was removed successfully",
        content = [Content(mediaType = "application/json")]
    )
    @Secured("USER")
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: UUID, authentication: Authentication): ResponseEntity<Any>

    @Operation(
        description = "This operation retrives a specify recipe by provided id from the system.",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @ApiResponse(
        responseCode = "200",
        description = "Recipe was retrived successfully",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = RecipeDto::class))]
    )
    @ApiResponse(
        responseCode = "404",
        description = "Recipe was not found",
        content = [Content(mediaType = "application/json")]
    )
    @Secured("USER")
    @GetMapping(value = ["/{id}"])
    fun getById(@PathVariable id : UUID): ResponseEntity<Any>


    @Operation(
        description = "This operation gets a recommendation of a recipe regarding the weather in some specify region",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @ApiResponse(
        responseCode = "200",
        description = "Recipe recommendation was retrived successfully",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = RecipeDto::class))]
    )
    @ApiResponse(
        responseCode = "404",
        description = "No suitable recipes found for the current weather",
        content = [Content(mediaType = "application/json")]
    )
    @Secured("USER")
    @GetMapping("/recommendation")
    fun getRecommendation(
        @RequestParam(required = false) t1: Double?,
        @RequestParam(required = false) t2: Double?
    ): ResponseEntity<Any>

}

data class RecipePageDto(
    val content: List<RecipeDto>,
    val pageable: Pageable,
    val totalElements: Long,
    val totalPages: Int,
    val last: Boolean,
    val first: Boolean,
    val sort: Any,
    val numberOfElements: Int,
    val size: Int,
    val number: Int,
    val empty: Boolean
)