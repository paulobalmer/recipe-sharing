package net.azeti.challenge.recipe.entrypoint.rest.controller

import net.azeti.challenge.recipe.core.usecase.*
import net.azeti.challenge.recipe.entrypoint.rest.dto.recipes.CreateRecipeRequestDto
import net.azeti.challenge.recipe.entrypoint.rest.mapper.RecipeDtoMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import com.fasterxml.jackson.databind.ObjectMapper
import net.azeti.challenge.recipe.core.domain.Recipe
import net.azeti.challenge.recipe.core.domain.User
import net.azeti.challenge.recipe.core.enums.IngredientUnitEnum
import net.azeti.challenge.recipe.entrypoint.rest.dto.recipes.IngredientDto
import net.azeti.challenge.recipe.entrypoint.rest.dto.recipes.RecipeDto
import net.azeti.challenge.recipe.entrypoint.rest.dto.users.UserDto
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import java.math.BigDecimal
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var createRecipeUseCase: CreateRecipeUseCase

    @MockBean
    private lateinit var updateRecipeUseCase: UpdateRecipeUseCase

    @MockBean
    private lateinit var deleteRecipeUseCase: DeleteRecipeUseCase

    @MockBean
    private lateinit var loadRecipeByIdUseCase: LoadRecipeByIdUseCase

    @MockBean
    private lateinit var loadUserByUsernameUseCase: LoadUserByUsernameUseCase

    @MockBean
    private lateinit var searchRecipeUseCase: SearchRecipeUseCase

    @MockBean
    private lateinit var recipeRecommendationUseCase: RecipeRecommendationUseCase

    @MockBean
    private lateinit var mapper: RecipeDtoMapper

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `create recipe should return created recipe on valid request with authentication`() {
        val createRecipeRequest = CreateRecipeRequestDto("title", "description", "instructions", listOf(
            IngredientDto(BigDecimal.valueOf(1.0), IngredientUnitEnum.pinch, "Salt")), "servings"
        )
        val user = User(
            UUID.randomUUID(),
            "user",
            "email@company.de",
            "Test User",
            "\$2a\$14\$YThB6.E8uzA5do2ZlA1Zc.WCqV1PaoXzvkbyGodTeojdJcONNsh16",
            true,
            Date()
        )
        val userDto = UserDto(
            UUID.randomUUID(),
            "user",
            "Test User",
            true
        )
        val authentication: Authentication = mock(Authentication::class.java)
        `when`(authentication.name).thenReturn("user")
        `when`(loadUserByUsernameUseCase.execute("user")).thenReturn(
            user
        )

        val recipe = Recipe(
            UUID.randomUUID(),
            createRecipeRequest.title,
            null,
            createRecipeRequest.description,
            createRecipeRequest.instructions,
            createRecipeRequest.servings,
            null,
            Date(),
            null)

        `when`(mapper.toDomain(createRecipeRequest)).thenReturn(
            Recipe(null,
                createRecipeRequest.title,
                null,
                createRecipeRequest.description,
                createRecipeRequest.instructions,
                createRecipeRequest.servings,
                null,
                Date(),
                null))

        `when`(createRecipeUseCase.execute(recipe, user)).thenReturn(recipe)
        `when`(mapper.toDto(recipe)).thenReturn(RecipeDto(
            UUID.randomUUID(),
            createRecipeRequest.title,
            userDto,
            createRecipeRequest.description,
            createRecipeRequest.instructions,
            createRecipeRequest.servings,
            listOf(IngredientDto(BigDecimal.valueOf(1.0), IngredientUnitEnum.pinch, "Salt")),
            Date(),
            null))

        mockMvc.perform(post("/recipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjectMapper().writeValueAsString(createRecipeRequest))
            .principal(authentication))
            .andExpect(status().isOk)
    }

}
