package ar.com.mitch.composeforbeginners.app.network.model

import ar.com.mitch.composeforbeginners.app.domain.model.Recipe
import ar.com.mitch.composeforbeginners.app.domain.util.EntityMapper

class RecipeNetworkMapper : EntityMapper<RecipeNetworkEntity, Recipe> {

    override fun mapFromEntity(entity: RecipeNetworkEntity): Recipe {
        return Recipe(
            id = entity.pk,
            title = entity.title,
            cookingInstructions = entity.cookingInstructions,
            dateAdded = entity.dateAdded,
            dateUpdated = entity.dateUpdated,
            description = entity.description,
            featuredImage = entity.featuredImage,
            ingredients = entity.ingredients.orEmpty(),
            publisher = entity.publisher,
            rating = entity.rating,
            sourceUrl = entity.sourceUrl
        )
    }

    override fun mapToEntity(domainModel: Recipe): RecipeNetworkEntity {
        return RecipeNetworkEntity(
            pk = domainModel.id,
            title = domainModel.title,
            cookingInstructions = domainModel.cookingInstructions,
            dateAdded = domainModel.dateAdded,
            dateUpdated = domainModel.dateUpdated,
            description = domainModel.description,
            featuredImage = domainModel.featuredImage,
            ingredients = domainModel.ingredients,
            publisher = domainModel.publisher,
            rating = domainModel.rating,
            sourceUrl = domainModel.sourceUrl
        )
    }

    fun fromEntityList(initial: List<RecipeNetworkEntity>): List<Recipe> {
        return initial.map { mapFromEntity(it) }
    }

    fun toEntityList(initial: List<Recipe>): List<RecipeNetworkEntity> {
        return initial.map { mapToEntity(it) }
    }

}