package dv.serg.lentanews

import android.app.Activity
import android.app.Application
import android.os.Bundle
import dv.serg.lentanews.component.AppComponent
import dv.serg.lentanews.component.DaggerAppComponent
import dv.serg.lentanews.component.DaggerLentaComponent
import dv.serg.lentanews.component.DaggerWebViewComponent
import dv.serg.lentanews.module.AppModule
import dv.serg.lentanews.module.LentaModule
import dv.serg.lentanews.module.WebViewModule
import dv.serg.lentanews.ui.ListActivity
import dv.serg.lentanews.ui.WebViewActivity
import dv.serg.lentanews.util.Injectable
import io.realm.Realm
import timber.log.Timber


class AppContext : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        val appModule = AppModule(this)
        appComponent = DaggerAppComponent.builder().appModule(appModule).build()

        Realm.init(this)


        if (BuildConfig.DEBUG) {
            // DebugTree has all usual logging functionality
            Timber.plant(Timber.DebugTree())

//            Stetho.initializeWithDefaults(this)
        }

        registerActivityLifecycleCallbacks(
                object : Application.ActivityLifecycleCallbacks {
                    override fun onActivityPaused(p0: Activity?) {
                        // STUB
                    }

                    override fun onActivityResumed(p0: Activity?) {
                        // STUB
                    }

                    override fun onActivityStarted(p0: Activity?) {
                        // STUB
                    }

                    override fun onActivityDestroyed(p0: Activity?) {
                        // STUB
                    }

                    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
                        // STUB
                    }

                    override fun onActivityStopped(p0: Activity?) {
                        // STUB
                    }

                    override fun onActivityCreated(activity: Activity?, p1: Bundle?) {
                        if (activity is Injectable) {
                            when (activity) {
                                is ListActivity -> {
                                    DaggerLentaComponent.builder().appComponent(appComponent)
                                            .lentaModule(LentaModule(activity)).build().inject(activity)
                                }
                                is WebViewActivity -> {
                                    DaggerWebViewComponent.builder()
                                            .webViewModule(WebViewModule(activity))
                                            .build().inject(activity)
                                }
                            }
                        }
                    }
                }
        )
    }
}