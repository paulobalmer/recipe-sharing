package net.azeti.challenge.recipe

import net.azeti.challenge.recipe.core.security.jwt.BCryptHasher
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RecipeSharingApplication

fun main(args: Array<String>) {
	println(BCryptHasher.encodePassword("admin"))
	runApplication<RecipeSharingApplication>(*args)
}
