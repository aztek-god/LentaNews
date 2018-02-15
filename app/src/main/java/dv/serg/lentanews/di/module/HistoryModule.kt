package dv.serg.lentanews.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dv.serg.lentanews.PerActivity
import dv.serg.lentanews.dao.impl.HistoryItemDao
import dv.serg.lentanews.presenter.HistoryPresenter

@Module
class HistoryModule(private val context: Context, private val view: HistoryPresenter.HistoryView) {
    @PerActivity
    @Provides
    fun providesPresenter(): HistoryPresenter {
        return HistoryPresenter(view, HistoryItemDao(context))
    }
}