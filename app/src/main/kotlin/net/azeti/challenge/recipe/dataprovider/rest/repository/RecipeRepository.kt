package net.azeti.challenge.recipe.dataprovider.rest.repository

import net.azeti.challenge.recipe.dataprovider.rest.entity.RecipeEntity

import org.springframework.data.repository.CrudRepository
import java.util.UUID
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface RecipeRepository : CrudRepository<RecipeEntity?, UUID?>, JpaSpecificationExecutor<RecipeEntity> {

    @EntityGraph(attributePaths = ["ingredients"])
    fun findWithIngredientsById(id: UUID): RecipeEntity?

}