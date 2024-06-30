package net.azeti.challenge.recipe.core.domain

import net.azeti.challenge.recipe.core.enums.IngredientUnitEnum
import java.math.BigDecimal
import java.util.*

data class Ingredient(
    var id: UUID?,
    var recipe: Recipe?,
    val value: BigDecimal,
    val unit: IngredientUnitEnum,
    var type: String,
)