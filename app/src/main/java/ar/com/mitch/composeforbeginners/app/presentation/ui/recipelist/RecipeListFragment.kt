package ar.com.mitch.composeforbeginners.app.presentation.ui.recipelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import ar.com.mitch.composeforbeginners.app.MainApplication
import ar.com.mitch.composeforbeginners.app.presentation.components.*
import ar.com.mitch.composeforbeginners.app.presentation.theme.AppTheme
import ar.com.mitch.composeforbeginners.app.util.RECIPE_LIST_PAGE_SIZE
import ar.com.mitch.composeforbeginners.app.util.SnackBarController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalComposeUiApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: MainApplication

    private val snackBarController = SnackBarController(lifecycleScope)

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                AppTheme(darkTheme = application.isDark.value) {
                    val isLoading = viewModel.loading.value
                    val recipes = viewModel.recipes.value
                    val query = viewModel.query.value
                    val selectedCategory = viewModel.selectedCategory.value
                    val scaffoldState = rememberScaffoldState()
                    val currentPage = viewModel.currentPage.value

                    Scaffold(
                        topBar = {
                            SearchToolbar(
                                query = query,
                                selectedCategory = selectedCategory,
                                onQueryChanged = viewModel::onQueryChange,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onExecuteSearch = {
                                    viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent)
                                },
                                scrollPosition = viewModel.categoryScrollPosition,
                                onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                                onToggleTheme = {
                                    application.toggleTheme()
                                }
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
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
                                        viewModel.onChangeRecipeListScrollPosition(index)
                                        if ((index + 1) >= (currentPage * RECIPE_LIST_PAGE_SIZE) && !isLoading) {
                                            viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent)
                                        }
                                        RecipeCard(
                                            recipe = recipe,
                                            onClick = {
                                                snackBarController.getScope().launch {
                                                    snackBarController.showSnackBar(
                                                        scaffoldState = scaffoldState,
                                                        message = recipe.title ?: "<EMPTY>",
                                                        actionLabel = "Hide"
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                            CircularIndeterminateProgressBar(isDisplayed = isLoading)
                            DefaultSnackBar(
                                snackBarHostState = scaffoldState.snackbarHostState,
                                onDismiss = {
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                },
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                }

            }
        }
    }
}