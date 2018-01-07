package dv.serg.lentanews.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import dv.serg.lentanews.R
import dv.serg.lentanews.holder.ListItemHolder
import dv.serg.lentanews.pojo.Article
import dv.serg.lentanews.pojo.Bookmark
import dv.serg.lentanews.util.UiAdapter
import io.realm.Realm
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


class ListItemAdapter(private val mData: MutableList<Article> = mutableListOf(), private val listener: (ListItemHolder) -> Unit = {}) : RecyclerView.Adapter<ListItemHolder>(), UiAdapter<Article> {

    override fun removeAt(index: Int) {
        mData.removeAt(index)
    }

    override fun restoreAt(index: Int, item: Article) {
        mData[index] = item
    }


    override fun onBindViewHolder(holder: ListItemHolder?, position: Int) {
        Timber.d("ListItemAdapter:onBindViewHolder:position = $position")
        holder!!.apply {
            Glide.with(itemView.context).load(mData[position].urlToImage).into(holder.thumbnail)
            Tim.d("article source name = ${mData[position]}")

            holder.dateTime.text = formatDate(mData[position].publishedAt ?: "")
            holder.shortDesc.text = mData[position].description
            holder.header.text = mData[position].title
            holder.fromSource.text = mData[position].source?.name?.replace(" ", "")

            applyClickEvents(holder, position)
        }
    }

    private fun applyClickEvents(holder: ListItemHolder, position: Int) {
        holder.listItem.setOnLongClickListener {
            Tim.d("ListItemAdapter:applyClickEvents")
            mData[position].isBookmarked = !mData[position].isBookmarked
            Tim.d("ListItemAdapter:applyClickEvents:mData[position].isBookmarked = ${mData[position].isBookmarked}")

            applyBookmarkState(holder, position)
            return@setOnLongClickListener true
        }
    }

    private fun applyBookmarkState(holder: ListItemHolder, position: Int) {
        val realm = Realm.getDefaultInstance()
        if (mData[position].isBookmarked) {
            holder.bookmark.setImageDrawable(holder.isBookmarkedTrue)

            realm.executeTransaction {
                with(mData[position]) {
                    val datetime = SimpleDateFormat("dd-MM-yyyy, HH:mm", Locale.ENGLISH)
                            .format(Calendar.getInstance().time)
                    //                          sourceName , title, desc, url, datetime, publishedAt
                    val bookmark = Bookmark(title = title!!, url = url!!, desc = description!!, datetime = datetime, publishedAt = publishedAt!!, sourceName = sourceName)

                    Tim.d("ListItemAdapter:executeTransaction:datetime = $datetime")
                    it.insertOrUpdate(bookmark)
                }
            }

            Toast.makeText(holder.listItem.context, "Bookmark has been added successfully!", Toast.LENGTH_SHORT).show()
        } else {
            holder.bookmark.setImageDrawable(holder.isBookmarkedFalse)
            Toast.makeText(holder.listItem.context, "Bookmark has been removed successfully!", Toast.LENGTH_SHORT).show()
        }
    }


    private fun formatDate(dateString: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)

        val date: Date = sdf.parse(dateString)

        val formatter = SimpleDateFormat("dd-MM-yyyy, hh:mm", Locale.ENGLISH)

        return formatter.format(date)
    }

    override fun getItemCount(): Int {
        Timber.d("item_count:${mData.size}")
        return mData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListItemHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_list, parent, false)

        Timber.d("ListItemAdapter:onCreateViewHolder")

        return ListItemHolder(view, listener = { listItemHolder: ListItemHolder ->
            listener.invoke(listItemHolder)
        })
    }


    override fun updateData(data: List<Article>) {
        this.mData.clear()
        this.mData.addAll(data)
    }

    override fun appendData(data: List<Article>) {
        this.mData += data
    }

    override fun notifyDataChanged() {
        notifyDataSetChanged()
    }

    override fun clear() {
        mData.clear()
    }

    override operator fun get(index: Int): Article = mData[index]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position
}
