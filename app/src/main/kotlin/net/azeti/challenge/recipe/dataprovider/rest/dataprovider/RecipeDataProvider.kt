package net.azeti.challenge.recipe.dataprovider.rest.dataprovider

import jakarta.persistence.criteria.Join
import jakarta.persistence.criteria.Predicate
import jakarta.transaction.Transactional
import net.azeti.challenge.recipe.core.dataprovider.IRecipeDataProvider
import net.azeti.challenge.recipe.core.domain.Ingredient
import net.azeti.challenge.recipe.core.domain.Recipe
import net.azeti.challenge.recipe.dataprovider.rest.entity.IngredientEntity
import net.azeti.challenge.recipe.dataprovider.rest.entity.RecipeEntity
import net.azeti.challenge.recipe.dataprovider.rest.entity.UserEntity
import net.azeti.challenge.recipe.dataprovider.rest.mapper.RecipeEntityMapper
import net.azeti.challenge.recipe.dataprovider.rest.repository.IngredientRepository
import net.azeti.challenge.recipe.dataprovider.rest.repository.RecipeRepository
import net.azeti.challenge.recipe.dataprovider.rest.repository.UserRepository
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.BusinessException
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.ObjectNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList


@Service
class RecipeDataProvider(
    val recipeRepository : RecipeRepository,
    val ingredientRepository: IngredientRepository,
    val userRepository: UserRepository,
    val recipeMapper : RecipeEntityMapper,
) : IRecipeDataProvider {

    @Transactional
    override fun save(recipe: Recipe, ingredients : List<Ingredient>): Recipe {
      val recipeEntity = recipeMapper.toEntity(recipe)
      val user = recipe.user?.id?.let { userRepository.findById(it) }
        if ((user != null && user.isEmpty) || user == null) {
            throw BusinessException("User not found with id {recipe.user.id}")
        }
      recipeEntity.user = user.get()
      val savedRecipe =  recipeRepository.save(recipeEntity)
      val ingredientsToRemove = ingredientRepository.listByRecipeId(savedRecipe.id)
      for (ingredient in ingredientsToRemove) {
          ingredientRepository.delete(ingredient)
      }
      val savedIngredients = ArrayList<IngredientEntity>()
      for (ingredient in ingredients) {
        val ingredientToSave = IngredientEntity(
            UUID.randomUUID(),
            savedRecipe,
            ingredient.value,
            ingredient.unit,
            ingredient.type,
            Date(),
            null
        )

        val savedIngredient = ingredientRepository.save(ingredientToSave)
        savedIngredients.add(savedIngredient)
      }
      savedRecipe.ingredients = savedIngredients
      return recipeMapper.toDomain(savedRecipe)
    }

    @Transactional
    override fun delete(id: UUID) {
        val ingredientsToRemove = ingredientRepository.listByRecipeId(id)
        for (ingredient in ingredientsToRemove) {
            ingredientRepository.delete(ingredient)
        }
        val recipeToRemove = recipeRepository.findById(id)
        recipeRepository.delete(recipeToRemove.get())
    }

    override fun listAll(): MutableIterable<RecipeEntity?> {
        return recipeRepository.findAll()
    }

    override fun getById(id: UUID): Recipe {
        val recipe = recipeRepository.findWithIngredientsById(id)
            ?: throw ObjectNotFoundException("Recipe not found with provided id")
        return recipeMapper.toDomain(recipe)
    }

    override fun search(username: String?, title: String?, pageable: Pageable): Page<Recipe> {
        val specification = Specification<RecipeEntity> { root, query, criteriaBuilder ->
            val predicates = mutableListOf<Predicate>()

            if (!username.isNullOrBlank()) {
                val userJoin: Join<RecipeEntity, UserEntity> = root.join("user")
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(userJoin.get("username")), "%${username.lowercase()}%"))
            }

            if (!title.isNullOrBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%${title.lowercase()}%"))
            }

            criteriaBuilder.and(*predicates.toTypedArray())
        }

        val pageWithEntities: Page<RecipeEntity> = recipeRepository.findAll(specification, pageable)
        val recipes: List<Recipe> = recipeMapper.toDomain(pageWithEntities.content)
        return PageImpl(recipes, pageWithEntities.pageable, pageWithEntities.totalElements)
    }





}