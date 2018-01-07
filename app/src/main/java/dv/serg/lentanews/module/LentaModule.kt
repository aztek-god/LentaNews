package dv.serg.lentanews.module

import dagger.Module
import dagger.Provides
import dv.serg.lentanews.PerActivity
import dv.serg.lentanews.pojo.Response
import dv.serg.lentanews.presenter.MainPresenter
import dv.serg.lentanews.ui.ListActivity
import dv.serg.lentanews.util.CrudDao
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.http.GET

@Module
class LentaModule(private val listActivity: ListActivity) {

    @PerActivity
    @Provides
    fun providePresenter(retrofit: Retrofit): MainPresenter =
            MainPresenter(listActivity, retrofit)

}