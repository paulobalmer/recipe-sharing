package net.azeti.challenge.recipe.core.domain

import java.util.*

data class User(
    val id: UUID?,
    val username: String,
    val name: String,
    val password: String,
    val active: Boolean?,
    var createdAt: Date?
)