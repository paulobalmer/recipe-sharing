package net.azeti.challenge.recipe.dataprovider.rest.repository

import net.azeti.challenge.recipe.dataprovider.rest.entity.RecipeEntity
import net.azeti.challenge.recipe.dataprovider.rest.entity.UserEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestPropertySource
import java.util.*

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(locations = ["classpath:application-test.yml"])
class RecipeRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var recipeRepository: RecipeRepository

    private var user: UserEntity = UserEntity(
        id = UUID.fromString("37ca0c45-2b6b-49a7-b093-3592e8d132b0"),
        username = "testuser",
        email = "test@user.com",
        name = "Test User",
        password = "password",
        active = true,
        createdAt = Date()
    )
    private var recipe: RecipeEntity = RecipeEntity(
        id = UUID.fromString("52738542-3b95-41e0-913f-4e85cdcf86f3"),
        title = "Test Recipe",
        ingredients = listOf(),
        user = user,
        description = "description",
        instructions = "instructions",
        servings = "servings",
        updatedAt = null,
        createdAt = Date()
    )

    @Test
    fun `should save recipe`() {
        val savedUser = userRepository.save(user)
        recipe.user = savedUser
        val savedRecipe = recipeRepository.save(recipe)

        assertNotNull(savedRecipe.id)
        assertEquals(recipe.title, savedRecipe.title)
    }

    @Test
    fun `should find recipe by id`() {
        val savedUser = userRepository.save(user)
        recipe.user = savedUser
        val savedRecipe = recipeRepository.save(recipe)
        val foundRecipe = recipeRepository.findById(savedRecipe.id!!)

        assertTrue(foundRecipe.isPresent)
        assertEquals(savedRecipe.id, foundRecipe.get().id)
    }

    @Test
    fun `should return empty when recipe not found by id`() {
        val foundRecipe = recipeRepository.findById(UUID.randomUUID())

        assertFalse(foundRecipe.isPresent)
    }

    @Test
    fun `should delete recipe by id`() {
        val savedUser = userRepository.save(user)
        recipe.user = savedUser
        val savedRecipe = recipeRepository.save(recipe)
        recipeRepository.deleteById(savedRecipe.id!!)

        val foundRecipe = recipeRepository.findById(savedRecipe.id!!)
        assertFalse(foundRecipe.isPresent)
    }
}
