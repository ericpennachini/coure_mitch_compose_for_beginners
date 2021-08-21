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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import ar.com.mitch.composeforbeginners.R

class RecipeListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        return ComposeView(requireContext()).apply {
//            setContent {
//                Text(text = "Hey, I'm a composable text!")
//            }
//        }
        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)

        view.findViewById<ComposeView>(R.id.compose_view).setContent {
            Column(
                modifier = Modifier
                    .border(border = BorderStroke(1.dp, Color.Blue))
                    .padding(16.dp)
            ) {
                Text(text = "THIS IS A COMPOSABLE INSIDE A FRAGMENT")
                Spacer(modifier = Modifier.padding(8.dp))
                CircularProgressIndicator()
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = "ANOTHER SHITTY TEXT")
                val customView = CustomView(requireContext(), "Here some text in a customView")
                AndroidView(factory = { customView })
            }
        }

        return view
    }
}