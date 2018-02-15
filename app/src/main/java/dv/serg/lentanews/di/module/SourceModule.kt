package dv.serg.lentanews.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dv.serg.lentanews.PerActivity
import dv.serg.lentanews.dao.impl.SourceItemDao
import dv.serg.lentanews.presenter.SourcePresenter

@Module
class SourceModule(val context: Context) {
    @Provides
    @PerActivity
    fun providePresenter(): SourcePresenter {
        return SourcePresenter(SourceItemDao(context))
    }
}