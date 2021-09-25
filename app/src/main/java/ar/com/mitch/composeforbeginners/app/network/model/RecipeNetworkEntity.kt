package ar.com.mitch.composeforbeginners.app.network.model

import com.google.gson.annotations.SerializedName

data class RecipeNetworkEntity(
    @SerializedName("pk")
    val pk: Int? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("publisher")
    val publisher: String? = null,

    @SerializedName("featured_image")
    val featuredImage: String? = null,

    @SerializedName("rating")
    val rating: Int? = 0,

    @SerializedName("source_url")
    val sourceUrl: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("cooking_instructions")
    val cookingInstructions: String? = null,

    @SerializedName("ingredients")
    val ingredients: List<String>? = null,

    @SerializedName("date_added")
    val dateAdded: String? = null,

    @SerializedName("date_updated")
    val dateUpdated: String? = null,

    @SerializedName("long_date_added")
    val longDateAdded: Long? = null,

    @SerializedName("long_date_updated")
    val longDateUpdated: Long? = null

)