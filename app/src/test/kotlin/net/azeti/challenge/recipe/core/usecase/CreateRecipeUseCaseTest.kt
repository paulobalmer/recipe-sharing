package net.azeti.challenge.recipe.core.usecase

import net.azeti.challenge.recipe.core.enums.IngredientUnitEnum
import java.math.BigDecimal

import net.azeti.challenge.recipe.core.dataprovider.IRecipeDataProvider
import net.azeti.challenge.recipe.core.domain.Recipe
import net.azeti.challenge.recipe.core.domain.User
import net.azeti.challenge.recipe.core.domain.Ingredient
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.BusinessException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class CreateRecipeUseCaseTest {

    @Mock
    lateinit var recipeDataProvider: IRecipeDataProvider

    @InjectMocks
    lateinit var createRecipeUseCase: CreateRecipeUseCase

    private val user = User(id = UUID.randomUUID(),
        username = "test",
        email = "email@provider.de",
        name = "Test User",
        password = "\$2a\$14\$YThB6.E8uzA5do2ZlA1Zc.WCqV1PaoXzvkbyGodTeojdJcONNsh16",
        active = true,
        createdAt = Date())

    val ingredients = listOf(
        Ingredient(id = UUID.randomUUID(), unit = IngredientUnitEnum.g, value = BigDecimal.valueOf(300), recipe = null, type = "Ingredient 1"),
        Ingredient(id = UUID.randomUUID(), unit = IngredientUnitEnum.pinch, value = BigDecimal.valueOf(100), recipe = null, type = "Ingredient 2")
    )

    val savedIngredients = listOf(
        Ingredient(id = UUID.randomUUID(), unit = IngredientUnitEnum.g, value = BigDecimal.valueOf(300), recipe = null, type = "Ingredient 1"),
        Ingredient(id = UUID.randomUUID(), unit = IngredientUnitEnum.pinch, value = BigDecimal.valueOf(100), recipe = null, type = "Ingredient 2")
    )

    val recipe = Recipe(
        id = UUID.randomUUID(),
        title = "Test Recipe",
        ingredients = null,
        user = user,
        description = "description",
        instructions = "instructions",
        servings = "servings",
        updatedAt = null,
        createdAt = Date()
    )

    @Test
    fun `should save recipe with ingredients`() {

        val recipe = this.recipe.copy(ingredients = ingredients)
        val savedRecipe = recipe.copy(id = UUID.randomUUID(), createdAt = Date())

        whenever(recipeDataProvider.save(any(), any())).thenReturn(savedRecipe)

        val result = createRecipeUseCase.execute(recipe, user)

        assertNotEquals(recipe.id, result.id, "Recipe ID should be a new UUID")
        assertEquals(user, result.user, "User should match the input user")
        assertEquals(ingredients.size, result.ingredients?.size, "Ingredients size should match")
    }

    @Test
    fun `should throw BusinessException when no ingredients`() {

        val recipe = this.recipe.copy(
            ingredients = null,
            user = user
        )

        val exception = assertThrows(BusinessException::class.java) {
            createRecipeUseCase.execute(recipe, user)
        }
        assertEquals("There are no ingredients in recipe", exception.message)
    }

    @Test
    fun `should set creation date for recipe`() {

        val recipe = this.recipe.copy(
            ingredients = ingredients,
            user = user,
            createdAt = Date()
        )
        val savedRecipe = recipe.copy(id = UUID.randomUUID(), createdAt = Date())

        whenever(recipeDataProvider.save(any(), any())).thenReturn(savedRecipe)


        val result = createRecipeUseCase.execute(recipe, user)


        assertNotNull(result.createdAt, "Creation date should be set")
        assertTrue(Date().time - result.createdAt!!.time < 1000, "Creation date should be recent")
    }

    @Test
    fun `should set new UUIDs for ingredients`() {

        val recipe = this.recipe.copy(
            ingredients = ingredients,
            user = user,
            createdAt = Date()
        )
        val savedRecipe = recipe.copy(id = UUID.randomUUID(), ingredients = savedIngredients, createdAt = Date())

        whenever(recipeDataProvider.save(any(), any())).thenReturn(savedRecipe)

        val result = createRecipeUseCase.execute(recipe, user)

        result.ingredients!!.forEach { ingredient ->
            assertNotEquals(ingredients.find { it.type == ingredient.type }!!.id, ingredient.id, "Ingredient ID should be a new UUID")
        }
    }

    @Test
    fun `should throw BusinessException when user is null`() {

        val recipe = this.recipe.copy(ingredients = this.ingredients, user = null)

        val exception = assertThrows(BusinessException::class.java) {
            createRecipeUseCase.execute(recipe, null)
        }
        assertEquals("User must not be null", exception.message)
    }
}
