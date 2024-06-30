package net.azeti.challenge.recipe.core.dataprovider

import net.azeti.challenge.recipe.core.domain.Ingredient
import net.azeti.challenge.recipe.core.domain.Recipe

interface IRecipeDataProvider {

    fun save(recipe: Recipe, ingredients : List<Ingredient>): Recipe

}