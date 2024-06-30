package net.azeti.challenge.recipe.core.usecase

import net.azeti.challenge.recipe.core.dataprovider.IUserDataProvider
import net.azeti.challenge.recipe.core.domain.User
import org.springframework.stereotype.Service

@Service
class LoadUserByUsernameUseCase(
    val userDataProvider : IUserDataProvider,
) {

    fun execute(username: String) : User? {
        return userDataProvider.findUserByUsername(username)
    }

}