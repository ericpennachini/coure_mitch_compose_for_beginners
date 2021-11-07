package ar.com.mitch.composeforbeginners.app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.com.mitch.composeforbeginners.app.domain.model.Recipe
import ar.com.mitch.composeforbeginners.app.util.RECIPE_LIST_PAGE_SIZE
import ar.com.mitch.composeforbeginners.app.util.SnackBarController
import kotlinx.coroutines.launch

@Composable
fun RecipeList(
    isLoading: Boolean,
    recipes: List<Recipe>,
    onChangeRecipeListScrollPosition: (Int) -> Unit,
    currentPage: Int,
    onNextPage: () -> Unit,
    onItemClick: (Recipe) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        if (isLoading && recipes.isEmpty()) {
            Skeleton(
                itemCount = 3,
                cardHeight = 250.dp
            )
        } else {
            LazyColumn {
                itemsIndexed(
                    items = recipes
                ) { index, recipe ->
                    onChangeRecipeListScrollPosition(index)
                    if ((index + 1) >= (currentPage * RECIPE_LIST_PAGE_SIZE) && !isLoading) {
                        onNextPage()
                    }
                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            onItemClick(recipe)
                        }
                    )
                }
            }
        }
    }
}