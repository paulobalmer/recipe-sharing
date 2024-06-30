package net.azeti.challenge.recipe.core.usecase

import net.azeti.challenge.recipe.core.dataprovider.IUserDataProvider
import net.azeti.challenge.recipe.core.domain.User
import net.azeti.challenge.recipe.core.security.jwt.BCryptHasher
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.BusinessException
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateUserUseCase(
    val userDataProvider : IUserDataProvider,
) {

    fun execute(user: User) : User {
        val existingUser = userDataProvider.findUserByUsername(user.username)
        if (existingUser != null) {
            throw BusinessException("User with username ${user.username} already exists")
        }

        val userToSave = user.copy(
            id = UUID.randomUUID(),
            password = BCryptHasher.encodePassword(user.password),
            active = true,
            createdAt = Date())
        return userDataProvider.insert(userToSave)
    }

}