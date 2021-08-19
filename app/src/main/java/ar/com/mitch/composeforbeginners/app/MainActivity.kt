package ar.com.mitch.composeforbeginners.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Space
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ar.com.mitch.composeforbeginners.R

class MainActivity : AppCompatActivity() {

    @ExperimentalUnitApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxHeight()
                    .background(color = Color(0xFFF1F1F1))
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(
                        res = resources,
                        id = R.drawable.happy_meal_small
                    ),
                    contentDescription = "A McDonnals hamburger",
                    modifier = Modifier.height(300.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Happy Meal",
                        style = TextStyle(
                            fontSize = TextUnit(
                                value = 26F,
                                type = TextUnitType.Sp
                            )
                        )
                    )
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    Text(
                        text = "800 calories",
                        style = TextStyle(
                            fontSize = TextUnit(
                                value = 18F,
                                type = TextUnitType.Sp
                            )
                        )
                    )
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    Text(
                        text = "$5,99",
                        style = TextStyle(
                            color = Color.Blue,
                            fontSize = TextUnit(
                                value = 18F,
                                type = TextUnitType.Sp
                            )
                        )
                    )
                }
            }

        }
    }

}