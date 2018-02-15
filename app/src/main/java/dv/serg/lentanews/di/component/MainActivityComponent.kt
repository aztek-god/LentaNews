package dv.serg.lentanews.di.component

import dagger.Component
import dv.serg.lentanews.PerActivity
import dv.serg.lentanews.di.module.BookmarkModule
import dv.serg.lentanews.di.module.HistoryModule
import dv.serg.lentanews.di.module.MainActivityModule
import dv.serg.lentanews.di.module.RetrofitModule
import dv.serg.lentanews.ui.activity.MainActivity

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(MainActivityModule::class, HistoryModule::class, BookmarkModule::class, RetrofitModule::class))
interface MainActivityComponent {
    fun inject(activity: MainActivity)
}