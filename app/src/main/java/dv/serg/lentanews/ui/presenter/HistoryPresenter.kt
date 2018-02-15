package dv.serg.lentanews.presenter

import dv.serg.lentanews.abstr.ParentView
import dv.serg.lentanews.dao.impl.HistoryItemDao
import dv.serg.lentanews.dao.room.entity.Bookmark
import dv.serg.lentanews.dao.room.entity.History
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HistoryPresenter(private val view: HistoryView, val dao: HistoryItemDao) {
    interface HistoryView : ParentView<History>

    fun loadData() {
        dao.getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(
                        { it ->
                            view.showData(it)
                        },
                        { it ->
                            view.showError(it.message.toString())
                        },
                        {
                            view.onComplete()
                        }
                )
    }

    fun insert(history: History) {
        dao.insert(history)
    }

    fun remove(history: History) {
        dao.delete(history)
    }

    fun removeAll() {
        dao.deleteAll()
    }
}