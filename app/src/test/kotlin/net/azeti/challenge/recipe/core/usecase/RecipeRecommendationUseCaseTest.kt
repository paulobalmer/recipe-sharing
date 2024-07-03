package net.azeti.challenge.recipe.core.usecase

import net.azeti.challenge.recipe.configuration.RecipeProperties
import net.azeti.challenge.recipe.configuration.RecipePropertiesTestConfiguration
import net.azeti.challenge.recipe.core.dataprovider.IRecipeDataProvider
import net.azeti.challenge.recipe.core.enums.IngredientUnitEnum
import net.azeti.challenge.recipe.dataprovider.rest.entity.IngredientEntity
import net.azeti.challenge.recipe.dataprovider.rest.entity.RecipeEntity
import net.azeti.challenge.recipe.dataprovider.rest.entity.UserEntity
import net.azeti.challenge.recipe.dataprovider.rest.mapper.RecipeEntityMapper
import net.azeti.challenge.recipe.dataprovider.rest.repository.RecipeRepository
import net.azeti.challenge.recipe.dataprovider.rest.repository.UserRepository
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.ObjectNotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal
import java.util.*
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.TestPropertySource

@ExtendWith(SpringExtension::class)
@SpringBootTest
@Import(RecipePropertiesTestConfiguration::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(locations = ["classpath:application-test.yml"])
class RecipeRecommendationUseCaseTest {

    @Mock
    lateinit var recipeDataProvider: IRecipeDataProvider

    @Mock
    lateinit var restTemplate: RestTemplate

    @Autowired
    lateinit var recipeProperties: RecipeProperties

    @Mock
    lateinit var mapper: RecipeEntityMapper

    @Autowired
    lateinit var recipeRecommendationUseCase: RecipeRecommendationUseCase

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var recipeRepository: RecipeRepository


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
    fun `should return empty list when no recommendations available`() {

        val exception = assertThrows<ObjectNotFoundException> {
            val recipe = recipeRecommendationUseCase.execute(28.0 ,2.0)
        }
        assert(exception.message == "No suitable recipes found for the current weather")

    }



}
