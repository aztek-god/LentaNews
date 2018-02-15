package dv.serg.lentanews.di.component

import android.app.Application
import dagger.Component
import dv.serg.lentanews.PerApplication
import dv.serg.lentanews.di.module.AppModule

@PerApplication
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun application(): Application
}