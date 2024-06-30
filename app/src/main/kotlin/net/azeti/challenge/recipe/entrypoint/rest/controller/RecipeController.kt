package net.azeti.challenge.recipe.entrypoint.rest.controller

import net.azeti.challenge.recipe.core.dataprovider.IUserDataProvider
import net.azeti.challenge.recipe.core.usecase.CreateRecipeUseCase
import net.azeti.challenge.recipe.entrypoint.rest.api.RecipeApi
import net.azeti.challenge.recipe.entrypoint.rest.dto.recipes.CreateRecipeRequestDto
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.BusinessException
import net.azeti.challenge.recipe.entrypoint.rest.mapper.RecipeDtoMapper
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.RestController

@RestController
class RecipeController(
    val createRecipeUseCase: CreateRecipeUseCase,
    val mapper : RecipeDtoMapper,
    val userDataProvider: IUserDataProvider
) : RecipeApi {

    override fun create(request: CreateRecipeRequestDto,  authentication: Authentication): ResponseEntity<Any> {
        val loggedUser = userDataProvider.findUserByUsername(authentication.name)
            ?: throw BusinessException("User info not found")

        val recipe = mapper.toDomain(request)
        return ResponseEntity.ok(mapper.toDto(createRecipeUseCase.execute(recipe, loggedUser)))
    }


}