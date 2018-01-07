package dv.serg.lentanews.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dv.serg.lentanews.PerActivity
import dv.serg.lentanews.ui.WebViewActivity
import dv.serg.lentanews.util.CrudDao
import timber.log.Timber

@Module
class WebViewModule(private val webViewActivity: WebViewActivity) {

    companion object {
        private const val SHARED_BOOKMARK = "SHARED_BOOKMARK"
    }

    @PerActivity
    @Provides
    fun provideSharedBookmarkDao(): CrudDao<String, String> {
        return object : CrudDao<String, String> {

            private val sharedPref = webViewActivity.application.getSharedPreferences(SHARED_BOOKMARK, Context.MODE_PRIVATE)

            override fun getById(id: String): String? {
                val url: String? = sharedPref.getString(id, null)
                Timber.d("provideSharedBookmarkDao:getById:$url")
                return url
            }

            override fun getAll(): List<String> {
                throw UnsupportedOperationException("not supported")
            }

            override fun save(item: String): Boolean {
                sharedPref.edit().putString(item, item).apply()

                return true
            }

            override fun update(item: String): Boolean {
                throw UnsupportedOperationException("not supported")
            }

            override fun delete(item: String): Boolean {
                if (sharedPref.contains(item)) {
                    sharedPref.edit().remove(item).apply()
                    return true
                }

                return false
            }

            override fun deleteById(id: String): Boolean {
                if (sharedPref.contains(id)) {
                    sharedPref.edit().remove(id).apply()
                    return true
                }

                return false
            }

            override fun deleteAll(): Boolean {
                throw UnsupportedOperationException("not supported")
            }

        }
    }

}