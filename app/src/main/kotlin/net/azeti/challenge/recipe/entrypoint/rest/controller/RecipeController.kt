package net.azeti.challenge.recipe.entrypoint.rest.controller

import net.azeti.challenge.recipe.core.usecase.*
import net.azeti.challenge.recipe.entrypoint.rest.api.RecipeApi
import net.azeti.challenge.recipe.entrypoint.rest.dto.recipes.CreateRecipeRequestDto
import net.azeti.challenge.recipe.entrypoint.rest.dto.recipes.RecipeDto
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.BusinessException
import net.azeti.challenge.recipe.entrypoint.rest.mapper.RecipeDtoMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class RecipeController(
    val createRecipeUseCase: CreateRecipeUseCase,
    val updateRecipeUseCase: UpdateRecipeUseCase,
    val deleteRecipeUseCase: DeleteRecipeUseCase,
    val loadRecipeByIdUseCase: LoadRecipeByIdUseCase,
    val loadUserByUsernameUseCase: LoadUserByUsernameUseCase,
    val searchRecipeUseCase: SearchRecipeUseCase,
    val recipeRecommendationUseCase: RecipeRecommendationUseCase,
    val mapper : RecipeDtoMapper
) : RecipeApi {



    override fun create(request: CreateRecipeRequestDto,  authentication: Authentication): ResponseEntity<Any> {
        val loggedUser = loadUserByUsernameUseCase.execute(authentication.name)
            ?: throw BusinessException("User info not found")
        val recipe = mapper.toDomain(request)
        return ResponseEntity.ok(mapper.toDto(createRecipeUseCase.execute(recipe, loggedUser)))
    }

    override fun update(id: UUID, request: CreateRecipeRequestDto, authentication: Authentication): ResponseEntity<Any> {
        val loggedUser = loadUserByUsernameUseCase.execute(authentication.name)
            ?: throw BusinessException("User info not found")
        val recipe = mapper.toDomain(request).copy(id = id)
        return ResponseEntity.ok(mapper.toDto(updateRecipeUseCase.execute(recipe, loggedUser)))
    }

    override fun delete(id: UUID, authentication: Authentication): ResponseEntity<Any> {
        val loggedUser = loadUserByUsernameUseCase.execute(authentication.name)
            ?: throw BusinessException("User info not found")
        deleteRecipeUseCase.execute(id, loggedUser)
        return ResponseEntity.noContent().build()
    }

    override fun listAll(username: String?, title: String?, pageable: Pageable): ResponseEntity<Page<RecipeDto>> {
        val recipes = searchRecipeUseCase.execute(username, title, pageable)
        return ResponseEntity.ok(recipes.map { mapper.toDto(it) })
    }

    override fun getById(id: UUID): ResponseEntity<Any> {
        return ResponseEntity.ok(mapper.toDto(loadRecipeByIdUseCase.execute(id)))
    }

    override fun getRecommendation(t1: Double?, t2: Double?): ResponseEntity<Any> {
        return ResponseEntity.ok(mapper.toDto(recipeRecommendationUseCase.execute(t1, t2)))
    }


}