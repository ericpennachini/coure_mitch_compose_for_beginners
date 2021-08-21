package ar.com.mitch.composeforbeginners.app

import android.content.Context
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import ar.com.mitch.composeforbeginners.R

class CustomView(
    context: Context,
    text: String
) : ConstraintLayout(context) {

    init {
        inflate(context, R.layout.view_custom, this)

        findViewById<TextView>(R.id.custom_view_text).text = text
    }

}