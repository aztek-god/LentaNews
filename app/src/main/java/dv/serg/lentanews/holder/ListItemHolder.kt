package dv.serg.lentanews.holder

import android.graphics.drawable.Drawable
import android.media.Image
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import dv.serg.lentanews.R

class ListItemHolder(private val view: View, listener: (ListItemHolder) -> Unit = { }) : RecyclerView.ViewHolder(view) {

    lateinit var listItem: ConstraintLayout

    init {
        listItem = view.findViewById<ConstraintLayout>(dv.serg.lentanews.R.id.listItemId)
        listItem.setOnClickListener {
            listener.invoke(this)
        }
    }

    val isBookmarkedTrue: Drawable by lazy {
        val drawable: Drawable = ContextCompat.getDrawable(view.context, R.drawable.ic_bookmark_orange_24dp)
        drawable
    }

    val isBookmarkedFalse: Drawable by lazy {
        val drawable: Drawable = ContextCompat.getDrawable(view.context, R.drawable.ic_bookmark_border_24dp)
        drawable
    }

    val dateTime: TextView by lazy {
        view.findViewById<TextView>(dv.serg.lentanews.R.id.dateTimeId)
    }

    val thumbnail: ImageView by lazy {
        view.findViewById<ImageView>(dv.serg.lentanews.R.id.thumbnailId)
    }

    val bookmark: ImageView by lazy {
        view.findViewById<ImageView>(dv.serg.lentanews.R.id.bookmarkId)
    }

    val fromSource: TextView by lazy {
        view.findViewById<TextView>(dv.serg.lentanews.R.id.fromSource)
    }


    val shortDesc: TextView by lazy {
        view.findViewById<TextView>(dv.serg.lentanews.R.id.shortDescId)
    }

    val header: TextView by lazy {
        view.findViewById<TextView>(dv.serg.lentanews.R.id.headerId)
    }

}