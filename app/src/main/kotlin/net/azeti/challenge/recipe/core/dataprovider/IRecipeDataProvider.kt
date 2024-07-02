package net.azeti.challenge.recipe.core.dataprovider

import net.azeti.challenge.recipe.core.domain.Ingredient
import net.azeti.challenge.recipe.core.domain.Recipe
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface IRecipeDataProvider {

    fun save(recipe: Recipe, ingredients : List<Ingredient>): Recipe

    fun getById(id: UUID): Recipe

    fun search(username : String?, title: String?, pageable: Pageable): Page<Recipe>

}