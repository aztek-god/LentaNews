package dv.serg.lentanews.presenter

import dv.serg.lentanews.abstr.ParentView
import dv.serg.lentanews.dao.impl.BookmarkItemDao
import dv.serg.lentanews.dao.room.entity.Bookmark
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BookmarkPresenter(private val view: BookmarkView, val dao: BookmarkItemDao) {
    interface BookmarkView : ParentView<Bookmark>

    fun loadData() {
        dao.getAll()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it: List<Bookmark> ->
                    view.showData(it)
                }, { it: Throwable ->
                    view.showError(it.message.toString())
                }, {
                    view.onComplete()
                })
    }

    fun remove(bookmark: Bookmark) {
        dao.delete(bookmark)
    }

    fun insert(bookmark: Bookmark) {
        dao.insert(bookmark)
    }

    fun removeAll() {
        dao.deleteAll()
    }
}