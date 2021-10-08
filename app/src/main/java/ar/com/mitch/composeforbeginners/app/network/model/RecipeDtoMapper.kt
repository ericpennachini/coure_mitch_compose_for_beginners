package ar.com.mitch.composeforbeginners.app.network.model

import ar.com.mitch.composeforbeginners.app.domain.model.Recipe
import ar.com.mitch.composeforbeginners.app.domain.util.DomainMapper

class RecipeDtoMapper : DomainMapper<RecipeDto, Recipe> {

    override fun mapToDomainModel(model: RecipeDto): Recipe {
        return Recipe(
            id = model.pk,
            title = model.title,
            cookingInstructions = model.cookingInstructions,
            dateAdded = model.dateAdded,
            dateUpdated = model.dateUpdated,
            description = model.description,
            featuredImage = model.featuredImage,
            ingredients = model.ingredients.orEmpty(),
            publisher = model.publisher,
            rating = model.rating,
            sourceUrl = model.sourceUrl
        )
    }

    override fun mapFromDomainModel(domainModel: Recipe): RecipeDto {
        return RecipeDto(
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

    fun toDomainList(initial: List<RecipeDto>): List<Recipe> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Recipe>): List<RecipeDto> {
        return initial.map { mapFromDomainModel(it) }
    }

}