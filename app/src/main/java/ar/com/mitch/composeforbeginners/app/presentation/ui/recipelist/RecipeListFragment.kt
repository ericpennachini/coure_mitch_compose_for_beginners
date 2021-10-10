package ar.com.mitch.composeforbeginners.app.presentation.ui.recipelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ar.com.mitch.composeforbeginners.app.presentation.components.RecipeCard
import ar.com.mitch.composeforbeginners.app.util.TAG
import dagger.hilt.android.AndroidEntryPoint

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

               Column {
                   OutlinedTextField(
                       value = query,
                       onValueChange = { newValue ->
                           viewModel.onQueryChange(newValue)
                       }
                   )
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