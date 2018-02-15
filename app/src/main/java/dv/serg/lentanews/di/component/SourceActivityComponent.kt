package dv.serg.lentanews.di.component

import dagger.Component
import dv.serg.lentanews.PerActivity
import dv.serg.lentanews.di.module.SourceModule
import dv.serg.lentanews.ui.activity.SourceActivity

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(SourceModule::class))
interface SourceActivityComponent {
    fun inject(activity: SourceActivity)
}
