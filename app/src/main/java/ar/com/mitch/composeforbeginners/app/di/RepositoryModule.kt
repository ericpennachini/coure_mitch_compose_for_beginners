package ar.com.mitch.composeforbeginners.app.di

import ar.com.mitch.composeforbeginners.app.network.RecipeService
import ar.com.mitch.composeforbeginners.app.network.model.RecipeDtoMapper
import ar.com.mitch.composeforbeginners.app.repository.RecipeRepository
import ar.com.mitch.composeforbeginners.app.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeService, recipeDtoMapper)
    }

}