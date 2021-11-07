package ar.com.mitch.composeforbeginners.app.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.mitch.composeforbeginners.app.domain.model.Recipe
import ar.com.mitch.composeforbeginners.app.repository.RecipeRepository
import ar.com.mitch.composeforbeginners.app.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import kotlin.Exception

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
) : ViewModel() {

    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading: MutableState<Boolean> = mutableStateOf(false)

    fun onTriggerEvent(event: RecipeEvent) {
        viewModelScope.launch {
            try {
                when(event) {
                    is RecipeEvent.GetRecipeEvent -> getRecipe(event.id)
                }
            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: ${e}, ${e.cause}")
            }
        }
    }

    private suspend fun getRecipe(recipeId: Int) {
        loading.value = true
        val recipe = repository.get(token = token, id = recipeId)
        this.recipe.value = recipe
        loading.value = false
    }

}