package net.azeti.challenge.recipe.dataprovider.rest.repository

import net.azeti.challenge.recipe.dataprovider.rest.entity.RecipeEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import java.util.UUID
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query

interface RecipeRepository : CrudRepository<RecipeEntity?, UUID?> {

    @EntityGraph(attributePaths = ["ingredients"])
    fun findWithIngredientsById(id: UUID): RecipeEntity?

    fun findAll(pageable: Pageable) : Page<RecipeEntity>

    @Query("SELECT r FROM RecipeEntity r WHERE LOWER(r.user.username) LIKE LOWER(CONCAT('%', :username, '%'))")
    fun findByUsernameContains(username: String, pageable: Pageable): Page<RecipeEntity>


}