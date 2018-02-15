package dv.serg.lentanews

import android.app.Activity
import android.app.Application
import android.os.Bundle
import dv.serg.lentanews.di.component.*
import dv.serg.lentanews.di.module.*
import dv.serg.lentanews.ui.activity.BookmarkActivity
import dv.serg.lentanews.ui.activity.HistoryActivity
import dv.serg.lentanews.ui.activity.MainActivity
import dv.serg.lentanews.ui.activity.SourceActivity
import timber.log.Timber


class AppContext : Application() {

    private lateinit var appComponent: AppComponent

    companion object {
        interface ActivityType {
            companion object {
                const val HISTORY = 101
                const val BOOKMARKS = 102
            }
        }

        interface TimePattern {
            companion object {
                const val PRETTY = "dd-MM-yyyy HH:mm:ss"
                const val CURRENT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        val appModule = AppModule(this)
        appComponent = DaggerAppComponent.builder().appModule(appModule).build()


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
                        when (activity) {
                            is MainActivity -> {
                                DaggerMainActivityComponent.builder().appComponent(appComponent)
                                        .mainActivityModule(MainActivityModule(activity))
                                        .build().inject(activity)
                            }
                            is HistoryActivity -> {
                                DaggerHistoryActivityComponent.builder().historyModule(HistoryModule(activity, activity)).build().inject(activity)
                            }
                            is BookmarkActivity -> {
                                DaggerBookmarkActivityComponent.builder().bookmarkModule(BookmarkModule(activity, activity)).build().inject(activity)
                            }
                            is SourceActivity -> {
                                DaggerSourceActivityComponent.builder().appComponent(appComponent)
                                        .sourceModule(SourceModule(activity))
                                        .build().inject(activity)
                            }
                        }
                    }
                }
        )
    }
}