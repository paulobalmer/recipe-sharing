package net.azeti.challenge.recipe.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "recipe")
data class RecipeProperties(
    var weather: Weather = Weather(),
    var coldRecipes: ColdRecipes = ColdRecipes(),
    var hotRecipes: HotRecipes = HotRecipes()
) {
    data class Weather(
        var api: Api = Api()
    ) {
        data class Api(
            var endpoint: String = "",
            var location: String = "",
            var units: String = "",
            var key: String = ""
        )
    }

    data class ColdRecipes(
        var temperature: Double = 0.0,
        var keywords: List<String> = emptyList()
    )

    data class HotRecipes(
        var temperature: Double = 0.0,
        var keywords: List<String> = emptyList()
    )
}
