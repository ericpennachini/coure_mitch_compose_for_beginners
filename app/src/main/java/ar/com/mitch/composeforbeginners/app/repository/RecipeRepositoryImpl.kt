package ar.com.mitch.composeforbeginners.app.repository

import ar.com.mitch.composeforbeginners.app.domain.model.Recipe
import ar.com.mitch.composeforbeginners.app.network.RecipeService
import ar.com.mitch.composeforbeginners.app.network.model.RecipeDtoMapper

class RecipeRepositoryImpl(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
) : RecipeRepository {

    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        val result = recipeService.search(token, page, query)
        return mapper.toDomainList(result.recipes)
    }

    override suspend fun get(token: String, id: Int): Recipe {
        val result = recipeService.get(token, id)
        return mapper.mapToDomainModel(result)
    }

}