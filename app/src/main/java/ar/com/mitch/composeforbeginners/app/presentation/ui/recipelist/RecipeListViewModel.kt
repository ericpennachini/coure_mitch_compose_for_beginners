package ar.com.mitch.composeforbeginners.app.presentation.ui.recipelist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.mitch.composeforbeginners.app.domain.model.Recipe
import ar.com.mitch.composeforbeginners.app.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
): ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query: MutableState<String> = mutableStateOf("")

    init {
        newSearch()
    }

    fun newSearch() {
        viewModelScope.launch {
            val result = repository.search(
                token = token,
                query = "chicken",
                page = 1
            )
            recipes.value = result
        }
    }

    fun onQueryChange(query: String) {
        this.query.value = query
    }

}