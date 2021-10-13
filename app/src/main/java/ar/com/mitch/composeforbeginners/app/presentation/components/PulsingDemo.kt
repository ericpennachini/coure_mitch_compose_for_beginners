package ar.com.mitch.composeforbeginners.app.presentation.components

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import ar.com.mitch.composeforbeginners.app.util.TAG

@Composable
fun PulsingDemo() {

    val color = MaterialTheme.colors.primary
    val infiniteTransition = rememberInfiniteTransition()

    val pulseMagnitude by infiniteTransition.animateFloat(
        initialValue = 40f,
        targetValue = 50f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .height(pulseMagnitude.dp)
                .width(pulseMagnitude.dp),
            imageVector = Icons.Default.Favorite,
            contentDescription = "Heart icon"
        )
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
    ) {
        drawCircle(
            radius = pulseMagnitude,
            brush = SolidColor(color)
        )
    }

    Log.d(TAG, "PulsingDemo: $pulseMagnitude")

}