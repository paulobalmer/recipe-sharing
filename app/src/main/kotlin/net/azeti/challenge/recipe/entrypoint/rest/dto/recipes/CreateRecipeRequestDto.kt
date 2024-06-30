package net.azeti.challenge.recipe.entrypoint.rest.dto.recipes

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CreateRecipeRequestDto(
    @field:NotNull(message = "Title is required")
    @field:Size(min = 2, max = 150, message = "Title must be between {min} and {max} characters")
    val title: String,

    val description: String?,

    @field:NotNull(message = "Instructions is required")
    @field:NotBlank(message = "Instructions cannot be blank")
    val instructions: String,

    @field:NotNull(message = "Ingredients are required")
    @field:NotEmpty(message = "Ingredients are required and must not be empty")
    val ingredients: List<IngredientDto> = mutableListOf(),

    var servings: String,
)