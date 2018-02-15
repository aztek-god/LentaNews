package dv.serg.lentanews.util

import android.app.Fragment
import android.content.Intent
import android.net.Uri
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import dv.serg.lentanews.AppContext.Companion.TimePattern.Companion.CURRENT
import dv.serg.lentanews.AppContext.Companion.TimePattern.Companion.PRETTY
import dv.serg.lentanews.R
import dv.serg.lentanews.dao.room.entity.Bookmark
import dv.serg.lentanews.dao.room.entity.History
import dv.serg.lentanews.pojo.Article
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

typealias Tim = Timber

fun timd(message: String) {
    Tim.d(message)
}

fun AppCompatActivity.setupActionBar(toolbar: Toolbar) {
    this.setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
}

fun <T : RecyclerView> FloatingActionButton.attachToRecycler(recycler: T, clickListener: () -> Unit) {
    setOnClickListener {
        clickListener.invoke()
    }

    recycler.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0 && visibility == View.VISIBLE) {
                        hide()
                    } else if (dy < 0 && visibility != View.VISIBLE) {
                        show()
                    }
                }
            }
    )

}

fun AppCompatActivity.startBrowser(url: String) {
    val chooserTitle = getString(R.string.browser_chooser_label)
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    val chooser = Intent.createChooser(intent, chooserTitle)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(chooser)
    }
}

fun AppCompatActivity.replaceFragment(container: Int, fragment: Fragment) {
    val fragmentManager = fragmentManager
    val transaction = fragmentManager.beginTransaction()
//    transaction.replace(container, fragment)
    transaction.add(fragment, "")
    transaction.commit()
}

fun getCurrentDatetime(pattern: String = PRETTY): String {

    return SimpleDateFormat(pattern, Locale.ENGLISH).format(Calendar.getInstance().time)
}

fun formatDate(dateString: String): String {
    val sdf = SimpleDateFormat(CURRENT, Locale.ENGLISH)

    val date: Date = sdf.parse(dateString)

    val formatter = SimpleDateFormat(PRETTY, Locale.ENGLISH)

    return formatter.format(date)
}

fun convertToHistory(article: Article): History {
    return History(title = article.title!!, sourceName = article.source?.name!!, shortDesc = article.description!!,
            datetime = article.publishedAt!!, url = article.url!!)
}

fun convertToBookmark(article: Article): Bookmark {
    return Bookmark(sourceName = article.source?.name!!, title = article.title!!, shortDesc = article.description!!,
            url = article.url!!)
}

