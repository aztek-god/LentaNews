package dv.serg.lentanews.component

import dagger.Component
import dagger.Module
import dv.serg.lentanews.PerActivity
import dv.serg.lentanews.module.WebViewModule
import dv.serg.lentanews.ui.WebViewActivity

@PerActivity
@Component(modules = arrayOf(WebViewModule::class))
interface WebViewComponent {
    fun inject(activity: WebViewActivity)
}