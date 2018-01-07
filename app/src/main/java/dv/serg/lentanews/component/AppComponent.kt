package dv.serg.lentanews.component

import android.app.Application
import dagger.Component
import dv.serg.lentanews.PerApplication
import dv.serg.lentanews.module.AppModule

@PerApplication
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun application(): Application
}