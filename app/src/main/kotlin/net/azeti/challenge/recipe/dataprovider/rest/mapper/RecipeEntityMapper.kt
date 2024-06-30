package net.azeti.challenge.recipe.dataprovider.rest.mapper

import net.azeti.challenge.recipe.core.domain.Ingredient
import net.azeti.challenge.recipe.core.domain.Recipe
import net.azeti.challenge.recipe.dataprovider.rest.entity.IngredientEntity
import net.azeti.challenge.recipe.dataprovider.rest.entity.RecipeEntity
import org.mapstruct.*
import org.springframework.stereotype.Component


@Component
@Mapper(componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface RecipeEntityMapper {

    @Mapping(target = "ingredients", qualifiedByName = ["toIngredientDomain"])
    fun toDomain(entity: RecipeEntity) : Recipe

    @Mapping(target = "ingredients", ignore = true)
    fun toEntity(domain: Recipe) : RecipeEntity

    @Named("toIngredientDomain")
    @Mapping(target = "recipe", ignore = true)
    fun toIngredientDomain(ingredient: IngredientEntity): Ingredient

    fun toDomain(content: List<RecipeEntity>): List<Recipe>




}