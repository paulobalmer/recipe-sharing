package net.azeti.challenge.recipe.core.usecase

import net.azeti.challenge.recipe.core.dataprovider.IRecipeDataProvider
import net.azeti.challenge.recipe.core.domain.Recipe
import net.azeti.challenge.recipe.core.domain.User
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.BusinessException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateRecipeUseCase(
    val recipeDataProvider : IRecipeDataProvider,
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun execute(recipe: Recipe, user: User?) : Recipe {

        if (user == null) {
            logger.error("User must not be null")
            throw BusinessException("User must not be null")
        }

        val recipeId = UUID.randomUUID()

        val recipeToSave = recipe.copy(
            id = recipeId,
            user = user,
            createdAt = Date()
        )
        if (recipe.ingredients != null) {
            for (ingredient in recipe.ingredients) {
                ingredient.id = UUID.randomUUID()
            }
            return recipeDataProvider.save(recipeToSave, recipe.ingredients)
        }
        logger.error("There are no ingredients in recipe")
        throw BusinessException("There are no ingredients in recipe")

    }
}