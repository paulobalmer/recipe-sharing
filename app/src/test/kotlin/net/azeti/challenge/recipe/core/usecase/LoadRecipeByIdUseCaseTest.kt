package net.azeti.challenge.recipe.core.usecase

import net.azeti.challenge.recipe.core.dataprovider.IRecipeDataProvider
import net.azeti.challenge.recipe.core.domain.Recipe
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.ObjectNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class LoadRecipeByIdUseCaseTest {

    @Mock
    lateinit var recipeDataProvider: IRecipeDataProvider

    @InjectMocks
    lateinit var loadRecipeByIdUseCase: LoadRecipeByIdUseCase

    private val recipeId = UUID.randomUUID()

    val recipe = Recipe(
        id = recipeId,
        title = "Test Recipe",
        ingredients = null,
        user = null,
        description = "description",
        instructions = "instructions",
        servings = "servings",
        updatedAt = null,
        createdAt = Date()
    )

    @Test
    fun `should return recipe by id`() {
        whenever(recipeDataProvider.getById(recipeId)).thenReturn(recipe)

        val result = loadRecipeByIdUseCase.execute(recipeId)

        assertNotNull(result, "Recipe should be present")
        assertEquals(recipe, result, "Returned recipe should match the expected recipe")
    }

    @Test
    fun `should return empty when recipe not found by id`() {
        whenever(recipeDataProvider.getById(recipeId)).thenReturn(null)

        val exception = assertThrows<ObjectNotFoundException> {
            loadRecipeByIdUseCase.execute(recipeId)
        }
        assert(exception.message == "Recipe not found with provided id")

    }
}
