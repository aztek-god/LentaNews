package dv.serg.lentanews.di.module

import dagger.Module
import dagger.Provides
import dv.serg.lentanews.PerActivity
import dv.serg.lentanews.dao.impl.BookmarkItemDao
import dv.serg.lentanews.dao.impl.HistoryItemDao
import dv.serg.lentanews.dao.impl.SourceItemDao
import dv.serg.lentanews.ui.presenter.MainPresenter
import dv.serg.lentanews.ui.activity.MainActivity
import retrofit2.Retrofit

@Module
class MainActivityModule(private val mainActivity: MainActivity) {

    @PerActivity
    @Provides
    fun providePresenter(retrofit: Retrofit): MainPresenter =
            MainPresenter(mainActivity, retrofit, SourceItemDao(mainActivity), HistoryItemDao(mainActivity), BookmarkItemDao(mainActivity))
}