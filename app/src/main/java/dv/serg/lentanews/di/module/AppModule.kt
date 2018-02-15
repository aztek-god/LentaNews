package dv.serg.lentanews.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import dv.serg.lentanews.PerApplication

@Module
class AppModule(private val appContext: Application) {
    @PerApplication
    @Provides
    fun provideApplication() = appContext
}