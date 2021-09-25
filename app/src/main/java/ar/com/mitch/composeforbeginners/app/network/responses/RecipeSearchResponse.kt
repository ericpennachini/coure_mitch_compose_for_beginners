package ar.com.mitch.composeforbeginners.app.network.responses

import ar.com.mitch.composeforbeginners.app.network.model.RecipeNetworkEntity
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(
    @SerializedName("count")
    val count: Int,

    @SerializedName("results")
    val results: List<RecipeNetworkEntity>
)