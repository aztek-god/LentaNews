package dv.serg.lentanews.ui.presenter

import dv.serg.lentanews.contract.MainContract
import dv.serg.lentanews.dao.abstr.Dao
import dv.serg.lentanews.dao.room.entity.Bookmark
import dv.serg.lentanews.dao.room.entity.History
import dv.serg.lentanews.pojo.Article
import dv.serg.lentanews.pojo.Response
import dv.serg.lentanews.dao.room.entity.SourceItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query


class MainPresenter(private val view: MainContract.MainView<List<Article>>,
                    private val retrofit: Retrofit,
                    private val sourceDao: Dao<Int, SourceItem>,
                    val historyDao: Dao<History, History>,
                    val bookmarkDao: Dao<Bookmark, Bookmark>)
    : MainContract.MainPresenter {

    private interface News {
        @GET("everything?sortBy=publishedAt")
        fun loadPopularNews(@Query("q") query: String, @Query("sources") sources: String, @Query("page") page: Int): Observable<Response>
    }

    private fun transformSources(sources: List<SourceItem>): String {
        return sources.joinToString(separator = ",", transform = { sourceItem ->
            sourceItem.sourceName
        })
    }

    inline fun <reified T> save(entity: T) {
        when (entity) {
            is History -> {
                historyDao.insert(entity as History)
            }
            is Bookmark -> {
                bookmarkDao.insert(entity as Bookmark)
            }
        }
    }

    inline fun <reified T> delete(entity: T) {
        when (entity) {
            is History -> {
                historyDao.delete(entity)
            }
            is Bookmark -> {
                bookmarkDao.delete(entity)
            }
        }
    }

    override fun loadData(query: String, page: Int) {
        view.onStartLoading()
        sourceDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doOnEach {
                }
                .subscribe({ it: List<SourceItem> ->
                    val sources: String = if (it.isEmpty()) {
                        "lenta"
                    } else {
                        transformSources(it)
                    }
                    retrofit.create(News::class.java)
                            .loadPopularNews(query, sources, page)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .unsubscribeOn(Schedulers.io())
                            .map {
                                it.articles
                            }
                            .cache()
                            .subscribe({ view.showData(it ?: emptyList()) }, { view.onError(it.message.toString()) }, { view.onComplete() })
                }, { view.onError(it.message ?: "unknown exception") })
    }
}