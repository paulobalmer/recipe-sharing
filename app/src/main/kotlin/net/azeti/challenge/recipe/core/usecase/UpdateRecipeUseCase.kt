package net.azeti.challenge.recipe.core.usecase

import net.azeti.challenge.recipe.core.dataprovider.IRecipeDataProvider
import net.azeti.challenge.recipe.core.domain.Recipe
import net.azeti.challenge.recipe.core.domain.User
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.BusinessException
import org.springframework.stereotype.Service
import java.util.*

@Service
class UpdateRecipeUseCase(
    val recipeDataProvider : IRecipeDataProvider,
) {
    fun execute(recipe: Recipe, user: User): Recipe {
        val existingRecipe = recipe.id?.let { recipeDataProvider.getById(it) }
            ?: throw BusinessException("Recipe not found")
        if (existingRecipe.user?.id != user.id) {
            throw BusinessException("User not authorized to update this recipe")
        }
        val recipeToSave = existingRecipe.copy(
            title = recipe.title,
            description = recipe.description,
            instructions = recipe.instructions,
            servings = recipe.servings,
            updatedAt = Date()
        )
        if (recipe.ingredients != null) {
            for (ingredient in recipe.ingredients) {
                ingredient.id = UUID.randomUUID()
            }
            return recipeDataProvider.save(recipeToSave, recipe.ingredients)
        }
        throw BusinessException("Error updating recipe")
    }
}
