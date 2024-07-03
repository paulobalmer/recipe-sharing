package net.azeti.challenge.recipe.dataprovider.rest.repository

import net.azeti.challenge.recipe.core.enums.IngredientUnitEnum
import net.azeti.challenge.recipe.dataprovider.rest.entity.IngredientEntity
import net.azeti.challenge.recipe.dataprovider.rest.entity.RecipeEntity
import net.azeti.challenge.recipe.dataprovider.rest.entity.UserEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestPropertySource
import java.math.BigDecimal
import java.util.*

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(locations = ["classpath:application-test.yml"])
class IngredientRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var recipeRepository: RecipeRepository

    @Autowired
    private lateinit var ingredientRepository: IngredientRepository

    private var user: UserEntity =  UserEntity(
                id = UUID.fromString("ed082532-7c89-46fe-8175-3d96dcb9b36f"),
                username = "testuser",
                email = "test@user.com",
                name = "Test User",
                password = "password",
                active = true,
                createdAt = Date()
            )

    private var recipe: RecipeEntity =  RecipeEntity(
            id = UUID.fromString("81e652c4-c6d3-4806-84e5-89f1ade94ed1"),
            title = "Test Recipe",
            ingredients = listOf(),
            user = UserEntity(
                id = UUID.fromString("ed082532-7c89-46fe-8175-3d96dcb9b36f"),
                username = "testuser",
                email = "test@user.com",
                name = "Test User",
                password = "password",
                active = true,
                createdAt = Date()
            ),
            description = "description",
            instructions = "instructions",
            servings = "servings",
            updatedAt = null,
            createdAt = Date()
    )

    private var ingredient: IngredientEntity = IngredientEntity(
        UUID.randomUUID(),
        RecipeEntity(
            id = UUID.fromString("81e652c4-c6d3-4806-84e5-89f1ade94ed1"),
            title = "Test Recipe",
            ingredients = listOf(),
            user = UserEntity(
                id = UUID.fromString("ed082532-7c89-46fe-8175-3d96dcb9b36f"),
                username = "testuser",
                email = "test@user.com",
                name = "Test User",
                password = "password",
                active = true,
                createdAt = Date()
            ),
            description = "description",
            instructions = "instructions",
            servings = "servings",
            updatedAt = null,
            createdAt = Date()
        ),
        BigDecimal.valueOf(300),
        IngredientUnitEnum.g,
        type = "Ingredient 1",
        Date(),
        null
    )

    @Test
    fun `should save ingredient`() {
        val savedUser = userRepository.save(user)
        recipe.user = savedUser
        val savedRecipe = recipeRepository.save(recipe)
        ingredient.recipe = savedRecipe
        val savedIngredient = ingredientRepository.save(ingredient)

        assertNotNull(savedIngredient.id)
        assertEquals(ingredient.type, savedIngredient.type)
    }

    @Test
    fun `should find ingredient by id`() {
        val savedUser = userRepository.save(user)
        recipe.user = savedUser
        val savedRecipe = recipeRepository.save(recipe)
        ingredient.recipe = savedRecipe
        val savedIngredient = ingredientRepository.save(ingredient)
        val foundIngredient = ingredientRepository.findById(savedIngredient.id)

        assertTrue(foundIngredient.isPresent)
        assertEquals(savedIngredient.id, foundIngredient.get().id)
    }

    @Test
    fun `should return empty when ingredient not found by id`() {
        val foundIngredient = ingredientRepository.findById(UUID.randomUUID())

        assertFalse(foundIngredient.isPresent)
    }

    @Test
    fun `should delete ingredient by id`() {
        val savedUser = userRepository.save(user)
        recipe.user = savedUser
        val savedRecipe = recipeRepository.save(recipe)
        ingredient.recipe = savedRecipe
        val savedIngredient = ingredientRepository.save(ingredient)
        ingredientRepository.deleteById(savedIngredient.id!!)

        val foundIngredient = ingredientRepository.findById(savedIngredient.id!!)
        assertFalse(foundIngredient.isPresent)
    }
}
