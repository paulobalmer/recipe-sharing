package net.azeti.challenge.recipe.entrypoint.rest.controller

import net.azeti.challenge.recipe.core.security.jwt.JwtUtil
import net.azeti.challenge.recipe.entrypoint.rest.dto.auth.LoginRequest
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import com.fasterxml.jackson.databind.ObjectMapper
import net.azeti.challenge.recipe.core.domain.User
import net.azeti.challenge.recipe.core.security.CustomUserDetails
import net.azeti.challenge.recipe.core.usecase.LoadUserByUsernameUseCase
import net.azeti.challenge.recipe.entrypoint.rest.dto.auth.AuthResponse
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var jwtUtil: JwtUtil

    @MockBean
    private lateinit var loadUserByUsernameUseCase: LoadUserByUsernameUseCase

    @MockBean
    private lateinit var authManager: AuthenticationManager

    @Test
    fun `login should return JWT token on valid credentials`() {
        val loginRequest = LoginRequest("administrator", "administrator")
        val authentication: Authentication = mock(Authentication::class.java)
        `when`(authManager.authenticate(any(UsernamePasswordAuthenticationToken::class.java))).thenReturn(authentication)
        `when`(authentication.principal).thenReturn(CustomUserDetails(
            User(
            UUID.randomUUID(),
            "administrator",
            "email@company.de",
        "Test User",
        "\$2a\$14\$YThB6.E8uzA5do2ZlA1Zc.WCqV1PaoXzvkbyGodTeojdJcONNsh16",
        true,
        Date()
        )))
        `when`(jwtUtil.generateToken("administrator")).thenReturn(AuthResponse("administrator", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbmlzdHJhdG9yIiwiZXhwIjoxNzIwMDAxNDY3LCJpYXQiOjE3MTk5OTc4Njd9.OGBeltAGNNPkDiRwaCPY3z2_6HNMaeXOe49U9qWdi7M", 1720001467650))

        mockMvc.perform(post("/auth/token")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjectMapper().writeValueAsString(loginRequest)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.accessToken").value("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbmlzdHJhdG9yIiwiZXhwIjoxNzIwMDAxNDY3LCJpYXQiOjE3MTk5OTc4Njd9.OGBeltAGNNPkDiRwaCPY3z2_6HNMaeXOe49U9qWdi7M"))
    }

    @Test
    fun `login should return unauthorized on invalid credentials`() {
        val loginRequest = LoginRequest("user", "wrong_password")
        `when`(authManager.authenticate(any(UsernamePasswordAuthenticationToken::class.java)))
            .thenThrow(BadCredentialsException::class.java)

        mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjectMapper().writeValueAsString(loginRequest)))
            .andExpect(status().isForbidden)
    }
}
