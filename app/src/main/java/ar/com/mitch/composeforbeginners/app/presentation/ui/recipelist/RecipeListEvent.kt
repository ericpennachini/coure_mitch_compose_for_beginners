package ar.com.mitch.composeforbeginners.app.presentation.ui.recipelist

sealed class RecipeListEvent {

    object NewSearchEvent : RecipeListEvent()

    object NextPageEvent : RecipeListEvent()

    object RestoreStateEvent : RecipeListEvent()

}
