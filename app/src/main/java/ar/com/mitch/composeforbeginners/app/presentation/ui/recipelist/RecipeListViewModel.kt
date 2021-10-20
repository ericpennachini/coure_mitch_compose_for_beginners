package ar.com.mitch.composeforbeginners.app.presentation.ui.recipelist

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.mitch.composeforbeginners.app.domain.model.Recipe
import ar.com.mitch.composeforbeginners.app.repository.RecipeRepository
import ar.com.mitch.composeforbeginners.app.util.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    @Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val loading: MutableState<Boolean> = mutableStateOf(false)
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query: MutableState<String> = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val currentPage: MutableState<Int> = mutableStateOf(1)
    private var recipeListScrollPosition = 0

    var categoryScrollPosition: Int = 0

    init {
        with(savedStateHandle) {
            get<Int>(STATE_KEY_PAGE)?.let { setCurrentPage(it) }
            get<String>(STATE_KEY_QUERY)?.let { setQuery(it) }
            get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { setSelectedCategory(it) }
            get<Int>(STATE_KEY_LIST_POSITION)?.let { setListScrollPosition(it) }
        }

        if (recipeListScrollPosition != 0) {
            onTriggerEvent(RecipeListEvent.NewSearchEvent)
        } else {
            onTriggerEvent(RecipeListEvent.RestoreStateEvent)
        }

    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeListEvent.NewSearchEvent -> newSearch()
                    is RecipeListEvent.NextPageEvent -> nextPage()
                    is RecipeListEvent.RestoreStateEvent -> restoreState()
                }
            } catch(ex: Exception) {
                Log.d(TAG, "onTriggerEvent: EXCEPTION = $ex")
            }
        }
    }

    private suspend fun newSearch() {
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

    fun onQueryChange(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        setSelectedCategory(newCategory)
        onQueryChange(category)
    }

    private suspend fun nextPage() {
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

    fun onChangeRecipeListScrollPosition(position: Int) {
        setListScrollPosition(position = position)
    }

    fun onChangeCategoryScrollPosition(position: Int) {
        categoryScrollPosition = position
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
        setCurrentPage(currentPage.value + 1)
    }

    private fun resetSelectedState() {
        recipes.value = listOf()
        currentPage.value = 1
        onChangeRecipeListScrollPosition(0)
        if (selectedCategory.value?.value != query.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
        setSelectedCategory(null)
    }

    private suspend fun restoreState() {
        loading.value = true
        val results: MutableList<Recipe> = mutableListOf()
        for (p in 1..currentPage.value) {
            val result = repository.search(
                token = token,
                page = p,
                query = query.value
            )
            results.addAll(result)
        }
        recipes.value = results
        loading.value = false
    }

    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setCurrentPage(page: Int) {
        currentPage.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    private fun setSelectedCategory(category: FoodCategory?) {
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }

}