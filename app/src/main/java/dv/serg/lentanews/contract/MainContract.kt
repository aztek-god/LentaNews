package dv.serg.lentanews.contract

import retrofit2.http.Query

interface MainContract {
    interface MainView<T> : View<T> {
        fun onStartLoading()
    }

    interface MainPresenter {
        fun loadData(query: String = "", page: Int = 1)
    }
}