package dv.serg.lentanews.presenter

import dv.serg.lentanews.contract.MainContract
import dv.serg.lentanews.pojo.Article
import dv.serg.lentanews.pojo.Response
import dv.serg.lentanews.pojo.SourceItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmResults
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber

typealias Tim = Timber

class MainPresenter(private val view: MainContract.MainView<List<Article>>, private val retrofit: Retrofit)
    : MainContract.MainPresenter {

    private interface News {
        @GET("everything?sortBy=publishedAt")
        fun loadPopularNews(@Query("q") query: String, @Query("sources") sources: String, @Query("page") page: Int): Observable<Response>
    }

    private fun loadStringSources(): String {
        val realm = Realm.getDefaultInstance()
        val realmList: RealmResults<SourceItem> = realm.where(SourceItem::class.java).findAll()

        val selectedSources: String = transformSources(realmList.toList())

        val unitedSources = "lenta," + selectedSources

        Tim.d("MainPresenter:loadStringSources:loadStringSources = $unitedSources")

        return unitedSources
    }

    private fun transformSources(sources: List<SourceItem>): String {
        return sources.joinToString(separator = ",", transform = { sourceItem ->
            sourceItem.sourceName
        })
    }

    override fun loadData(query: String, page: Int) {
        view.onStartLoading()
        retrofit.create(News::class.java)
                .loadPopularNews(query, loadStringSources(), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .map {
                    it.articles
                }
//                .cache()
                .subscribe({ view.showData(it ?: emptyList()) }, { view.onError(it.message.toString()) }, { view.onComplete() })
    }
}