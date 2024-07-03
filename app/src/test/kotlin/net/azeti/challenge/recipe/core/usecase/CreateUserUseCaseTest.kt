package net.azeti.challenge.recipe.core.usecase

import net.azeti.challenge.recipe.core.dataprovider.IUserDataProvider
import net.azeti.challenge.recipe.core.domain.User
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
class CreateUserUseCaseTest {

    @Mock
    lateinit var userDataProvider: IUserDataProvider

    @InjectMocks
    lateinit var createUserUseCase: CreateUserUseCase

    val user = User(
        id = UUID.randomUUID(),
        username = "testuser",
        email = "test@user.com",
        name = "Test User",
        password = "password",
        active = true,
        createdAt = Date()
    )

    @Test
    fun `should create new user`() {
        whenever(userDataProvider.insert(any())).thenReturn(user)

        val result = createUserUseCase.execute(user)

        assertEquals(user, result, "Returned user should match the expected user")
    }

}
