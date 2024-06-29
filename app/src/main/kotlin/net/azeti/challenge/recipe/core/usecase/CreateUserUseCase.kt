package net.azeti.challenge.recipe.core.usecase

import net.azeti.challenge.recipe.core.dataprovider.IUserDataProvider
import net.azeti.challenge.recipe.core.domain.User
import net.azeti.challenge.recipe.core.security.jwt.BCryptHasher
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateUserUseCase(
    val userDataProvider : IUserDataProvider,
) {

    fun execute(user: User) : User? {
        val userToSave = user.copy(password = BCryptHasher.encodePassword(user.password))
        return userDataProvider.insert(userToSave)
    }

}