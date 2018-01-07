package dv.serg.lentanews.ui

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebViewClient
import android.widget.Toast
import dv.serg.lentanews.R
import dv.serg.lentanews.util.CrudDao
import dv.serg.lentanews.util.Injectable
import kotlinx.android.synthetic.main.activity_web_view.*
import timber.log.Timber
import javax.inject.Inject

class WebViewActivity : AppCompatActivity(), Injectable {
    companion object {
        val URL_DESCRIPTOR = "URL_DESCRIPTOR"
    }

    @Inject lateinit var sharedDao: CrudDao<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        setSupportActionBar(webViewToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""

        val url = intent.extras[URL_DESCRIPTOR] as String
        webView.loadUrl(url)
        webView.isEnabled = false
        webView.webViewClient = WebViewClient()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.browser, menu)

        Timber.d("onCreateOptionsMenu")

        if (isBookmarked(webView.url)) {
            // change icon color
            tintMenuIcon(menu?.getItem(0), R.color.colorAccent)
        } else {
            tintMenuIcon(menu?.getItem(0), android.R.color.white)
        }

        return true
    }

    private fun isBookmarked(url: String): Boolean {

        val value: String? = sharedDao.getById(url)

        Timber.d("isBookmarked:value = $value")

        val flag: Boolean = value != null

        if (flag) {
            if (sharedDao.getById(url) == url) {
                return true
            }
        }
        return false
    }


    private fun tintMenuIcon(item: MenuItem?, color: Int) {
        item?.icon?.let {
            it.mutate()
            it.setColorFilter(ContextCompat.getColor(this, color), PorterDuff.Mode.SRC_ATOP)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }

        var isBookmarked = false

        if (item?.itemId == R.id.action_bookmark) {
            if (!isBookmarked(webView.url)) {
                Timber.d("!isBookmarked")
                isBookmarked = true
                sharedDao.save(webView.url)
            } else {
                Timber.d("isBookmarked")
                sharedDao.delete(webView.url)
            }

            invalidateOptionsMenu()

            if (isBookmarked) {
                Toast.makeText(this, "Your bookmark has been added.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Your bookmark has been removed.", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
