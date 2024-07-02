package net.azeti.challenge.recipe.core.usecase

import net.azeti.challenge.recipe.core.dataprovider.IRecipeDataProvider
import net.azeti.challenge.recipe.core.domain.Recipe
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SearchRecipeUseCase(
    val recipeDataProvider : IRecipeDataProvider,
) {

    fun execute(username: String?, title : String?, pageable: Pageable) : Page<Recipe> {
        return recipeDataProvider.search(username, title, pageable)
    }

}