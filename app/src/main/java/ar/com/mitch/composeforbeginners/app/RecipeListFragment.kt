package ar.com.mitch.composeforbeginners.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ar.com.mitch.composeforbeginners.R

class RecipeListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
           setContent {
               Column(modifier = Modifier.padding(16.dp)) {
                   Text(
                       text = "Recipe List",
                       style = TextStyle(
                           fontSize = 21.sp
                       )
                   )
                   Spacer(modifier = Modifier.padding(12.dp))
                   Button(onClick = {
                       findNavController().navigate(R.id.toRecipeFragment)
                   }) {
                       Text(text = "TO RECIPE FRAGMENT")
                   }
               }
           }
        }

    }
}