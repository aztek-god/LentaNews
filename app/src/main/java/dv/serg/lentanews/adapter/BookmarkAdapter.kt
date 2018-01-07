package dv.serg.lentanews.adapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dv.serg.lentanews.R
import dv.serg.lentanews.pojo.Bookmark

class BookmarkAdapter(private val mData: MutableList<Bookmark> = ArrayList(), private val clickListener: (view: View, position: Int) -> Unit = { _, _ -> run {} },
                      private val longListener: (view: View, position: Int) -> Unit = { _, _ -> run {} })
    : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    val data: List<Bookmark> get() = mData.toList()

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(mData[position], clickListener, longListener)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.bookmark_item, parent, false)

        return ViewHolder(view)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bookmarkTile: ConstraintLayout = view.findViewById(R.id.bookmarkTile)
        private val bookmarkSource: TextView = view.findViewById(R.id.bookmarkSource)
        private val bookmarkTitle: TextView = view.findViewById(R.id.bookmarkTitle)
        private val bookmarkDesc: TextView = view.findViewById(R.id.bookmarkDesc)
        private val bookmarkDatetime: TextView = view.findViewById(R.id.bookmarkDatetime)

        fun bind(bookmark: Bookmark, clickListener: (view: View, position: Int) -> Unit = { _, _ -> run {} }, longListener: (view: View, position: Int) -> Unit = { _, _ -> run {} }) {
            bookmarkTile.setOnClickListener {
                clickListener.invoke(bookmarkTile, adapterPosition)
            }

            bookmarkTile.setOnLongClickListener {
                longListener.invoke(bookmarkTile, adapterPosition)
                return@setOnLongClickListener true
            }

            with(bookmark) {
                bookmarkSource.text = sourceName
                bookmarkTitle.text = title
                bookmarkDesc.text = desc
                bookmarkDatetime.text = datetime
            }
        }
    }
}