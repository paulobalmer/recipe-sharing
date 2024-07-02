package net.azeti.challenge.recipe

import net.azeti.challenge.recipe.configuration.RecipeProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RecipeProperties::class)
class RecipeSharingApplication

fun main(args: Array<String>) {
	runApplication<RecipeSharingApplication>(*args)
}
