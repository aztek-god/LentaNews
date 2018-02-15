package dv.serg.lentanews.di.component

import dagger.Component
import dv.serg.lentanews.PerActivity
import dv.serg.lentanews.di.module.BookmarkModule
import dv.serg.lentanews.ui.activity.BookmarkActivity

@PerActivity
@Component(modules = arrayOf(BookmarkModule::class))
interface BookmarkActivityComponent {
    fun inject(activity: BookmarkActivity)
}