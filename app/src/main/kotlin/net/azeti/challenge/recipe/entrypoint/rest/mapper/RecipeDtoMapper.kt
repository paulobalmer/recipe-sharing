package net.azeti.challenge.recipe.entrypoint.rest.mapper

import net.azeti.challenge.recipe.core.domain.Recipe
import net.azeti.challenge.recipe.entrypoint.rest.dto.recipes.CreateRecipeRequestDto
import net.azeti.challenge.recipe.entrypoint.rest.dto.recipes.RecipeDto
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import org.springframework.stereotype.Component

@Component
@Mapper(componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface RecipeDtoMapper {

    fun toDomain(createRecipeRequest : CreateRecipeRequestDto) : Recipe

    fun toDto(domain: Recipe): RecipeDto

    fun toDto(domain: List<Recipe>): List<RecipeDto>

}