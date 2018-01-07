package dv.serg.lentanews.component

import dagger.Component
import dv.serg.lentanews.PerActivity
import dv.serg.lentanews.module.LentaModule
import dv.serg.lentanews.module.RetrofitModule
import dv.serg.lentanews.ui.ListActivity

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(LentaModule::class, RetrofitModule::class))
interface LentaComponent {
    fun inject(listActivity: ListActivity)
}