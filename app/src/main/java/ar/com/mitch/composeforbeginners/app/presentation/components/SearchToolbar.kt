package ar.com.mitch.composeforbeginners.app.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ar.com.mitch.composeforbeginners.app.presentation.ui.recipelist.FoodCategory
import ar.com.mitch.composeforbeginners.app.presentation.ui.recipelist.getAllFoodCategories

@Composable
fun SearchToolbar(
    query: String,
    selectedCategory: FoodCategory?,
    onQueryUpdated: (newValue: String) -> Unit,
    onSearchPerformed: () -> Unit,
    onSelectedCategoryChanged: (String) -> Unit
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
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
                onValueChange = onQueryUpdated,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchPerformed()
                    }
                ),
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onSurface
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = MaterialTheme.colors.surface
                )
            )
        }
        LazyRow(
            modifier = Modifier.padding(
                start = 8.dp,
                bottom = 8.dp
            )
        ) {
            itemsIndexed(
                items = getAllFoodCategories()
            ) { _, cat ->
                FoodCategoryChip(
                    category = cat.value,
                    isSelected = selectedCategory == cat,
                    onSelectedCategoryChanged = onSelectedCategoryChanged,
                    onExecuteSearch = {
                        onQueryUpdated(cat.value)
                        onSearchPerformed()
                    }
                )
            }
        }
    }
}