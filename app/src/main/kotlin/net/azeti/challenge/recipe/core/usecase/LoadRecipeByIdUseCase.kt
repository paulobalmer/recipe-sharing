package net.azeti.challenge.recipe.core.usecase

import net.azeti.challenge.recipe.core.dataprovider.IRecipeDataProvider
import net.azeti.challenge.recipe.core.domain.Recipe
import org.springframework.stereotype.Service
import java.util.*

@Service
class LoadRecipeByIdUseCase(
    val recipeDataProvider : IRecipeDataProvider,
) {

    fun execute(id: UUID) : Recipe {
        return recipeDataProvider.getById(id)
    }

}