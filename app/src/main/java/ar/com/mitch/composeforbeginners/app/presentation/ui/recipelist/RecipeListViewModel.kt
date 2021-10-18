package ar.com.mitch.composeforbeginners.app.presentation.ui.recipelist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.mitch.composeforbeginners.app.domain.model.Recipe
import ar.com.mitch.composeforbeginners.app.repository.RecipeRepository
import ar.com.mitch.composeforbeginners.app.util.RECIPE_LIST_PAGE_SIZE
import ar.com.mitch.composeforbeginners.app.util.TAG
import ar.com.mitch.composeforbeginners.app.util.loadPicture
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
): ViewModel() {

    val loading: MutableState<Boolean> = mutableStateOf(false)
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query: MutableState<String> = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val currentPage: MutableState<Int> = mutableStateOf(1)
    private var recipeListScrollPosition = 0

    var categoryScrollPosition: Int = 0

    init {
        newSearch()
    }

    fun newSearch() {
        viewModelScope.launch {
            loading.value = true
            resetSelectedState()
            val result = repository.search(
                token = token,
                query = query.value,
                page = 1
            )
            recipes.value = result
            loading.value = false
        }
    }

    fun onQueryChange(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChange(category)
    }

    fun nextPage() {
        viewModelScope.launch {
            // preventing duplicate events due to recomposition happening to quickly
            if ((recipeListScrollPosition + 1) >= currentPage.value * RECIPE_LIST_PAGE_SIZE) {
                loading.value = true
                incrementPage()
                Log.d(TAG, "nextPage: triggered: ${currentPage.value}")

                delay(1000) // delete this!!

                if (currentPage.value > 1) {
                    val result = repository.search(
                        token = token,
                        page = currentPage.value,
                        query = query.value
                    )
                    Log.d(TAG, "nextPage: $result")
                    appendRecipes(result)
                    loading.value = false
                }
            }
        }
    }

    fun onChangeRecipeListScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }

    /**
     * Append new recipes to the current list of recipes
     */
    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage() {
        currentPage.value++
    }

    private fun resetSelectedState() {
        recipes.value = listOf()
        currentPage.value = 1
        onChangeRecipeListScrollPosition(0)
        if (selectedCategory.value?.value != query.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    fun onChangeCategoryScrollPosition(position: Int) {
        categoryScrollPosition = position
    }

}