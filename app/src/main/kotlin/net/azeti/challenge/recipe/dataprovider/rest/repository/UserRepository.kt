package net.azeti.challenge.recipe.dataprovider.rest.repository

import org.springframework.data.repository.CrudRepository
import java.util.UUID
import net.azeti.challenge.recipe.dataprovider.rest.entity.UserEntity

interface UserRepository : CrudRepository<UserEntity?, UUID?> {

    fun findByUsername(username: String) : UserEntity?

}