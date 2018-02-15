package dv.serg.lentanews.di.component

import dagger.Component
import dv.serg.lentanews.PerActivity
import dv.serg.lentanews.di.module.HistoryModule
import dv.serg.lentanews.ui.activity.HistoryActivity

@PerActivity
@Component(modules = arrayOf(HistoryModule::class))
interface HistoryActivityComponent {
    fun inject(activity: HistoryActivity)
}