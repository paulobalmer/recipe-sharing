package net.azeti.challenge.recipe.dataprovider.rest.dataprovider

import net.azeti.challenge.recipe.core.domain.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserDataProviderTest {

    @MockBean
    private lateinit var userDataProvider: UserDataProvider

    private lateinit var user: User

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
    }

    @Test
    fun `should find user by username`() {
        `when`(userDataProvider.findUserByUsername(user.username)).thenReturn(user)

        val foundUser = userDataProvider.findUserByUsername(user.username)

        assertTrue(foundUser != null)
        assertEquals(user, foundUser)
    }

    @Test
    fun `should return empty when user not found by username`() {
        `when`(userDataProvider.findUserByUsername("nonexistentuser")).thenReturn(null)

        val foundUser = userDataProvider.findUserByUsername("nonexistentuser")

        assertFalse(foundUser != null)
    }

}
