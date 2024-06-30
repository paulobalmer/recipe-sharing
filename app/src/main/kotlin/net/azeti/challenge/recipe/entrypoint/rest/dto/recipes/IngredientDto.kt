package net.azeti.challenge.recipe.entrypoint.rest.dto.recipes

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import net.azeti.challenge.recipe.core.enums.IngredientUnitEnum
import java.math.BigDecimal

data class IngredientDto(
    @field:NotNull(message = "Value is required")
    @field:DecimalMin(value = "0.01", message = "Value must be greater than zero")
    val value: BigDecimal,

    @field:NotNull(message = "Unit is required")
    val unit: IngredientUnitEnum,

    @field:NotNull(message = "Type is required")
    @field:Size(min = 2, max = 120, message = "Type must be between {min} and {max} characters")
    var type: String,
)