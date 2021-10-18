package ar.com.mitch.composeforbeginners.app.util

import androidx.compose.material.ScaffoldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackBarController(
    private val coroutineScope: CoroutineScope
) {
    private var snackBarJob: Job? = null

    init {
        cancelActiveJob()
    }

    fun getScope() = coroutineScope

    fun showSnackBar(
        scaffoldState: ScaffoldState,
        message: String,
        actionLabel: String
    ) {
        snackBarJob = if (snackBarJob == null) {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message, actionLabel)
                cancelActiveJob()
            }
        } else {
            cancelActiveJob()
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(message, actionLabel)
                cancelActiveJob()
            }
        }
    }

    private fun cancelActiveJob() {
        snackBarJob?.let {
            it.cancel()
            snackBarJob = Job()
        }
    }

}