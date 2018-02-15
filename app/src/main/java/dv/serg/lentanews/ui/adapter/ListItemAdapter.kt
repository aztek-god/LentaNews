package dv.serg.lentanews.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import dv.serg.lentanews.R
import dv.serg.lentanews.pojo.Article
import dv.serg.lentanews.util.ClickListener
import dv.serg.lentanews.util.formatDate


class ListItemAdapter(var mData: MutableList<Article>? = mutableListOf(), private val listener: MaintainBookmarkState)
    : RecyclerView.Adapter<ListItemAdapter.ListItemHolder>() {

    val data: List<Article> get() = mData?.toList() ?: emptyList()

    fun clear() {
        mData?.clear()
        notifyDataSetChanged()
    }

    fun removeAt(index: Int) {
        mData?.removeAt(index)
        notifyDataSetChanged()
    }

    fun restoreAt(index: Int, item: Article) {
        mData?.add(index, item)
        notifyDataSetChanged()
    }

    fun addAll(collection: List<Article>) {
        mData?.addAll(collection)
        notifyDataSetChanged()
    }

    interface MaintainBookmarkState : ClickListener {
        fun changeListener(position: Int)
    }

    override fun onBindViewHolder(holder: ListItemHolder?, position: Int) {
        holder!!.bind(mData!![position], listener)
    }

    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListItemHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_list, parent, false)

        return ListItemHolder(view)
    }

    inner class ListItemHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val dateTime: TextView = view.findViewById(dv.serg.lentanews.R.id.dateTimeId)
        private val fromSource: TextView = view.findViewById(dv.serg.lentanews.R.id.fromSource)
        private val shortDesc: TextView = view.findViewById(dv.serg.lentanews.R.id.shortDescId)
        private val header: TextView = view.findViewById(dv.serg.lentanews.R.id.headerId)
        private val thumbnail: ImageView = view.findViewById(dv.serg.lentanews.R.id.thumbnailId)

        fun bind(article: Article?, listener: MaintainBookmarkState) {
            if (article != null) {
                with(article) {
                    dateTime.text = formatDate(publishedAt ?: "")
                    header.text = title
                    shortDesc.text = description
                    fromSource.text = source?.name
                    Glide.with(itemView.context).load(mData!![adapterPosition].urlToImage).into(thumbnail)
                    view.setOnClickListener {
                        listener.onClick(adapterPosition)
                    }
                }
            }

            view.setOnLongClickListener {
                val articleItem: Article = mData!![adapterPosition]
                articleItem.isBookmarked = !articleItem.isBookmarked
                listener.changeListener(adapterPosition)

                return@setOnLongClickListener true
            }
        }
    }
}
