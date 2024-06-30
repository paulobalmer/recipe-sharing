package net.azeti.challenge.recipe.core.dataprovider

import net.azeti.challenge.recipe.core.domain.Ingredient
import net.azeti.challenge.recipe.core.domain.Recipe
import java.util.*

interface IRecipeDataProvider {

    fun save(recipe: Recipe, ingredients : List<Ingredient>): Recipe

    fun getById(id: UUID): Recipe

}