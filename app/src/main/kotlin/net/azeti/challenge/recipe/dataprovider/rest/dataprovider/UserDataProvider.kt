package net.azeti.challenge.recipe.dataprovider.rest.dataprovider

import net.azeti.challenge.recipe.core.dataprovider.IUserDataProvider
import net.azeti.challenge.recipe.core.domain.User
import net.azeti.challenge.recipe.dataprovider.rest.mapper.UserEntityMapper
import net.azeti.challenge.recipe.dataprovider.rest.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserDataProvider(
    val userRepository : UserRepository,
    val mapper : UserEntityMapper
) : IUserDataProvider {

    override fun insert(user: User): User {
      return mapper.toDomain(userRepository.save(mapper.toEntity(user)))
    }

    override fun findUserByUsername(username: String): User? {
        return userRepository.findByUsername(username)?.let { mapper.toDomain(it) }
    }

    override fun findUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)?.let { mapper.toDomain(it) }
    }

}