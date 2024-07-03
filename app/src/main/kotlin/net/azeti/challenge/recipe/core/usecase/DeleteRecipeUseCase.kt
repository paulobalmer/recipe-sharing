package net.azeti.challenge.recipe.core.usecase

import net.azeti.challenge.recipe.core.dataprovider.IRecipeDataProvider
import net.azeti.challenge.recipe.core.domain.User
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.BusinessException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeleteRecipeUseCase(
    val recipeDataProvider : IRecipeDataProvider,
) {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun execute(id: UUID, user: User) {
        val existingRecipe = recipeDataProvider.getById(id)
            ?: throw BusinessException("Recipe not found")
        if (existingRecipe.user?.id != user.id) {
            throw BusinessException("User not authorized to delete this recipe")
        }
        recipeDataProvider.delete(id)
    }
}