package net.azeti.challenge.recipe.dataprovider.rest.repository

import net.azeti.challenge.recipe.dataprovider.rest.entity.UserEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestPropertySource
import java.util.*

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(locations = ["classpath:application-test.yml"])
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    private lateinit var user: UserEntity

    @BeforeEach
    fun setUp() {
        user = UserEntity(
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
    fun `should save user`() {
        val savedUser = userRepository.save(user)

        assertNotNull(savedUser.id)
        assertEquals(user.username, savedUser.username)
    }

    @Test
    fun `should find user by id`() {
        val savedUser = userRepository.save(user)
        val foundUser = userRepository.findById(savedUser.id!!)

        assertTrue(foundUser.isPresent)
        assertEquals(savedUser.id, foundUser.get().id)
    }

    @Test
    fun `should return empty when user not found by id`() {
        val foundUser = userRepository.findById(UUID.randomUUID())

        assertFalse(foundUser.isPresent)
    }

    @Test
    fun `should find user by username`() {
        val savedUser = userRepository.save(user)
        val foundUser = userRepository.findByUsername(savedUser.username)

        assertTrue(foundUser != null)
        assertEquals(savedUser.username, foundUser?.username)
    }

    @Test
    fun `should return empty when user not found by username`() {
        val foundUser = userRepository.findByUsername("nonexistentuser")

        assertTrue(foundUser == null)
    }

    @Test
    fun `should delete user by id`() {
        val savedUser = userRepository.save(user)
        userRepository.deleteById(savedUser.id)

        val foundUser = userRepository.findById(savedUser.id)
        assertFalse(foundUser.isPresent)
    }
}
