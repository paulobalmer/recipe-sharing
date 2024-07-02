package net.azeti.challenge.recipe.core.usecase

import net.azeti.challenge.recipe.configuration.RecipeProperties
import net.azeti.challenge.recipe.core.dataprovider.IRecipeDataProvider
import net.azeti.challenge.recipe.core.domain.Recipe
import net.azeti.challenge.recipe.dataprovider.rest.mapper.RecipeEntityMapper
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.BusinessException
import net.azeti.challenge.recipe.entrypoint.rest.errorhandler.exception.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class RecipeRecommendationUseCase(
    val recipeDataProvider : IRecipeDataProvider,
    val restTemplate: RestTemplate,
    val recipeProperties: RecipeProperties,
    val mapper: RecipeEntityMapper
) {

    fun execute(t1: Double?, t2: Double?) : Recipe {
        val temperatureThresholdHigh = t1 ?: recipeProperties.hotRecipes.temperature
        val temperatureThresholdLow = t2 ?: recipeProperties.coldRecipes.temperature

        val weatherApiUrl = "${recipeProperties.weather.api.endpoint}/${recipeProperties.weather.api.location}?unitGroup=${recipeProperties.weather.api.units}&key=${recipeProperties.weather.api.key}&contentType=json"
        val response = restTemplate.getForEntity(weatherApiUrl, Map::class.java)
        val weatherData = response.body ?: throw BusinessException("Weather data not available")

        val currentHour = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:00:00"))
        val currentHourData = (weatherData["days"] as List<*>)[0] as Map<*, *>
        val hours = currentHourData["hours"] as List<Map<*, *>>
        val currentHourTemp = hours.find { it["datetime"] == currentHour }?.get("temp") as? Double
            ?: return throw BusinessException("Current hour temperature data not available")
        println("currentHourTemp $currentHourTemp")
        val recipes = recipeDataProvider.listAll()
        val filteredRecipes = recipes.filter { recipe ->
            val isHotRecipe = recipeProperties.hotRecipes.keywords.any { word -> recipe?.servings?.contains(word, ignoreCase = true) == true ||
                recipe?.title?.contains(word, ignoreCase = true) ?:false  ||
                recipe?.instructions?.contains(word, ignoreCase = true) ?:false  ||
                recipe?.description?.contains(word, ignoreCase = true) == true
            }

            val isColdRecipe = recipeProperties.coldRecipes.keywords.any { word -> recipe?.servings?.contains(word, ignoreCase = true) == true ||
                    recipe?.title?.contains(word, ignoreCase = true) ?:false  ||
                    recipe?.instructions?.contains(word, ignoreCase = true) ?: false ||
                    recipe?.description?.contains(word, ignoreCase = true) == true }

            val matchesHot = (currentHourTemp <= temperatureThresholdHigh || !isHotRecipe) &&
                    (currentHourTemp >= temperatureThresholdLow || !isColdRecipe)
            matchesHot
        }

        if (filteredRecipes.isEmpty()) {
            throw ObjectNotFoundException("No suitable recipes found for the current weather")
        }

        val recommendedRecipe = filteredRecipes.random()
        if (recommendedRecipe != null) {
            return mapper.toDomain(recommendedRecipe)
        }
        throw ObjectNotFoundException("No suitable recipes found for the current weather")
    }

}