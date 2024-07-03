package net.azeti.challenge.recipe.entrypoint.rest.controller

import net.azeti.challenge.recipe.core.usecase.CreateUserUseCase
import net.azeti.challenge.recipe.entrypoint.rest.dto.users.CreateUserRequestDto
import net.azeti.challenge.recipe.entrypoint.rest.mapper.UserDtoMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import com.fasterxml.jackson.databind.ObjectMapper
import net.azeti.challenge.recipe.core.domain.User
import net.azeti.challenge.recipe.core.security.jwt.JwtUtil
import net.azeti.challenge.recipe.core.usecase.LoadUserByUsernameUseCase
import net.azeti.challenge.recipe.entrypoint.rest.dto.users.CreateUserResponseDto
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var createUserUseCase: CreateUserUseCase

    @MockBean
    private lateinit var loadUserByUsernameUseCase: LoadUserByUsernameUseCase

    @MockBean
    private lateinit var jwtUtil: JwtUtil

    @MockBean
    private lateinit var mapper: UserDtoMapper

    @Test
    fun `create user should return created user on valid request`() {
        val createUserRequest = CreateUserRequestDto("Test User","username", "email@company.de", "password")
        val user = User(
            UUID.randomUUID(),
            "username",
            "email@company.de",
            "Test User",
            "\$2a\$14\$YThB6.E8uzA5do2ZlA1Zc.WCqV1PaoXzvkbyGodTeojdJcONNsh16",
            true,
            Date()
        )

        `when`(mapper.toDomain(createUserRequest)).thenReturn(user)

        `when`(createUserUseCase.execute(user)).thenReturn(user)

        `when`(mapper.toCreateUserResponseDto(user)).thenReturn(
            CreateUserResponseDto(UUID.randomUUID(),"username", "Test user", true))

        mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjectMapper().writeValueAsString(createUserRequest)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.username").value("username"))
    }

}
