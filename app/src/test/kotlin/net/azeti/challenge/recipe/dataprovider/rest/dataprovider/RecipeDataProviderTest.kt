package net.azeti.challenge.recipe.dataprovider.rest.dataprovider

import net.azeti.challenge.recipe.core.domain.Ingredient
import net.azeti.challenge.recipe.core.domain.Recipe
import net.azeti.challenge.recipe.core.domain.User
import net.azeti.challenge.recipe.core.enums.IngredientUnitEnum
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.ObjectNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.math.BigDecimal
import java.util.*

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class RecipeDataProviderTest {

    @MockBean
    private lateinit var recipeDataProvider: RecipeDataProvider

    private lateinit var recipe: Recipe
    private lateinit var user: User
    private lateinit var ingredients: List<Ingredient>

    @BeforeEach
    fun setUp() {
        user = User(
            id = UUID.randomUUID(),
            username = "testuser",
            email = "test@user.com",
            name = "Test User",
            password = "password",
            active = true,
            createdAt = Date()
        )

        ingredients = listOf(
            Ingredient(id = UUID.randomUUID(), unit = IngredientUnitEnum.g, value = BigDecimal.valueOf(300), recipe = null, type = "Ingredient 1"),
            Ingredient(id = UUID.randomUUID(), unit = IngredientUnitEnum.pinch, value = BigDecimal.valueOf(100), recipe = null, type = "Ingredient 2")
        )

        recipe = Recipe(
            id = UUID.randomUUID(),
            title = "Test Recipe",
            ingredients = ingredients,
            user = user,
            description = "description",
            instructions = "instructions",
            servings = "servings",
            updatedAt = null,
            createdAt = Date()
        )

    }

    @Test
    fun `should find recipe by id`() {
        `when`(recipeDataProvider.getById(recipe.id!!)).thenReturn(recipe)

        val foundRecipe = recipeDataProvider.getById(recipe.id!!)

        assertEquals(recipe, foundRecipe)
    }

    @Test
    fun `should return empty when recipe not found by id`() {
        val randomId = UUID.randomUUID()
        `when`(recipeDataProvider.getById(randomId)).thenThrow(ObjectNotFoundException("Recipe not found with provided id"))

        val exception = assertThrows(ObjectNotFoundException::class.java) {
            recipeDataProvider.getById(randomId)
        }
        assertEquals("Recipe not found with provided id", exception.message)
    }

    @Test
    fun `should delete recipe by id`() {
        doNothing().`when`(recipeDataProvider).delete(recipe.id!!)

        assertDoesNotThrow {
            recipeDataProvider.delete(recipe.id!!)
        }
    }
}
