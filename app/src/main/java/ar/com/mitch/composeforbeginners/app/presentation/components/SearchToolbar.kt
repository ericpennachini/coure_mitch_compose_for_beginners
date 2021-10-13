package ar.com.mitch.composeforbeginners.app.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import ar.com.mitch.composeforbeginners.app.presentation.ui.recipelist.FoodCategory
import ar.com.mitch.composeforbeginners.app.presentation.ui.recipelist.getAllFoodCategories
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun SearchToolbar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    scrollPosition: Int,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChanged: (String) -> Unit,
    onChangeCategoryScrollPosition: (Int) -> Unit,
    onToggleTheme: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface,
        elevation = 8.dp
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                val keyboard = LocalSoftwareKeyboardController.current
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp),
                    value = query,
                    leadingIcon = {
                        Icon(Icons.Filled.Search, "Search")
                    },
                    label = {
                        Text(text = "Search...")
                    },
                    onValueChange = onQueryChanged,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onExecuteSearch()
                            keyboard?.hide()
                        }
                    ),
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.onSurface
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = MaterialTheme.colors.surface
                    ),
                    shape = MaterialTheme.shapes.large
                )
                ConstraintLayout(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    val menu = createRef()
                    IconButton(
                        onClick = onToggleTheme,
                        modifier = Modifier.constrainAs(menu) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                    ) {
                        Icon(Icons.Filled.WbSunny, "Enable dark theme")
                    }
                }
            }
            val lazyListState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()
            LazyRow(
                modifier = Modifier.padding(
                    start = 8.dp,
                    bottom = 8.dp
                ),
                state = lazyListState
            ) {
                coroutineScope.launch {
                    lazyListState.scrollToItem(scrollPosition)
                }
                itemsIndexed(
                    items = getAllFoodCategories()
                ) { _, cat ->
                    FoodCategoryChip(
                        category = cat.value,
                        isSelected = selectedCategory == cat,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChanged(it)
                            onChangeCategoryScrollPosition(lazyListState.firstVisibleItemIndex)
                        },
                        onExecuteSearch = {
                            onQueryChanged(cat.value)
                            onExecuteSearch()
                        }
                    )
                }
            }
        }
    }
}