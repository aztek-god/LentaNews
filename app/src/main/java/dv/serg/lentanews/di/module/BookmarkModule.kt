package dv.serg.lentanews.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dv.serg.lentanews.PerActivity
import dv.serg.lentanews.dao.impl.BookmarkItemDao
import dv.serg.lentanews.presenter.BookmarkPresenter

@Module
class BookmarkModule(private val context: Context, private val view: BookmarkPresenter.BookmarkView) {
    @PerActivity
    @Provides
    fun providePresenter(): BookmarkPresenter {
        return BookmarkPresenter(view, BookmarkItemDao(context))
    }
}