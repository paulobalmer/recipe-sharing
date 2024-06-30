package net.azeti.challenge.recipe.core.domain

import java.util.*

data class Recipe(
    val id: UUID?,
    val title: String,
    val user : User?,
    val description: String?,
    val instructions: String,
    var servings: String,
    val ingredients: List<Ingredient>? = mutableListOf(),
    var createdAt: Date?,
    var updatedAt: Date?
)