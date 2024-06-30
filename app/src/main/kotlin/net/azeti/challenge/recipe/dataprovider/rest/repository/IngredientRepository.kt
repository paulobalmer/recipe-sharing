package net.azeti.challenge.recipe.dataprovider.rest.repository

import net.azeti.challenge.recipe.dataprovider.rest.entity.IngredientEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID
import org.springframework.data.jpa.repository.Query

interface IngredientRepository : CrudRepository<IngredientEntity?, UUID?> {

    @Query("Select i from IngredientEntity i where i.recipe.id = :recipeId")
    fun listByRecipeId(recipeId: UUID): List<IngredientEntity>


}