package ar.com.mitch.composeforbeginners.app.di

import android.content.Context
import ar.com.mitch.composeforbeginners.app.MainApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext context: Context): MainApplication {
        return context as MainApplication
    }

}