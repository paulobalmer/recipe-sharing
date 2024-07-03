package net.azeti.challenge.recipe.configuration

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class RecipePropertiesTestConfiguration {

    @Bean
    fun recipeProperties(): RecipeProperties {
        return RecipeProperties().apply {
            weather = RecipeProperties.Weather().apply {
                api = RecipeProperties.Weather.Api().apply {
                    endpoint = "http://test-endpoint"
                    location = "test-location"
                    units = "metric"
                    key = "test-key"
                }
            }
            coldRecipes = RecipeProperties.ColdRecipes().apply {
                temperature = 15.0
                keywords = listOf("salad", "smoothie")
            }
            hotRecipes = RecipeProperties.HotRecipes().apply {
                temperature = 25.0
                keywords = listOf("soup", "stew")
            }
        }
    }
}
