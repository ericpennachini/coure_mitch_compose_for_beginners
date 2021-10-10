package ar.com.mitch.composeforbeginners.app.presentation.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ar.com.mitch.composeforbeginners.app.presentation.components.RecipeCard
import ar.com.mitch.composeforbeginners.app.presentation.components.SearchToolbar
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalComposeUiApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val recipes = viewModel.recipes.value
                val query = viewModel.query.value
                val selectedCategory = viewModel.selectedCategory.value

                Column {
                    Surface(
                       modifier = Modifier.fillMaxWidth(),
                       color = MaterialTheme.colors.background,
                       elevation = 8.dp
                    ) {
                       val keyboard = LocalSoftwareKeyboardController.current
                       SearchToolbar(
                           query = query,
                           selectedCategory = selectedCategory,
                           onQueryUpdated = viewModel::onQueryChange,
                           onSelectedCategoryChanged = {
                               viewModel.onSelectedCategoryChanged(it)
                           },
                           onSearchPerformed = {
                               viewModel.newSearch()
                               keyboard?.hide()
                           }
                       )
                    }
                    LazyColumn {
                       itemsIndexed(
                           items = recipes
                       ) { _, recipe ->
                           RecipeCard(
                               recipe = recipe,
                               onClick = {
                                   Toast.makeText(
                                       requireContext(),
                                       "Clicked: ${recipe.title}",
                                       Toast.LENGTH_SHORT
                                   ).show()
                               }
                           )
                       }
                    }
                }
           }
        }
    }
}