package net.azeti.challenge.recipe.entrypoint.rest.dto.recipes

import net.azeti.challenge.recipe.entrypoint.rest.dto.users.UserDto
import java.util.*

data class RecipeDto(
    val id: UUID,
    val title: String,
    val user : UserDto,
    val description: String?,
    val instructions: String,
    var servings: String,
    val ingredients: List<IngredientDto> = mutableListOf(),
    var createdAt: Date?,
    var updatedAt: Date?
)