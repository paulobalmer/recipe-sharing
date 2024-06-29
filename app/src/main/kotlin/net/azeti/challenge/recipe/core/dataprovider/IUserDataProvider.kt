package net.azeti.challenge.recipe.core.dataprovider

import net.azeti.challenge.recipe.core.domain.User

interface IUserDataProvider {

    fun insert(user: User): User

    fun findUserByUsername(username: String): User?

}