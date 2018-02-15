package dv.serg.lentanews.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dv.serg.lentanews.R
import dv.serg.lentanews.adapter.abstr.StableIdAdapter
import dv.serg.lentanews.dao.room.entity.History

class HistoryAdapter(var mData: MutableList<History> = ArrayList(), var clickListener: (View, Int) -> Unit = { _, _ -> })
    : StableIdAdapter<HistoryAdapter.ViewHolder>() {

    val data: List<History> get() = mData.toList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.history_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(mData[position], clickListener)
    }

    class ViewHolder(val view: View)
        : RecyclerView.ViewHolder(view) {

        private val historySourceFrom: TextView = view.findViewById(R.id.historySourceFrom)
//        private val historyDatetime: TextView = view.findViewById(R.id.historyDatetime)
        private val historyShortDescription: TextView = view.findViewById(R.id.historyShortDescription)
        private val historyVisitedAt: TextView = view.findViewById(R.id.historyVisitedAt)
        private val historyTitle: TextView = view.findViewById(R.id.historyTitle)

        fun bind(history: History, clickListener: (View, Int) -> Unit = { _, _ -> }) {
            view.setOnClickListener {
                clickListener.invoke(view, adapterPosition)
            }

            historySourceFrom.text = history.sourceName
//            historyDatetime.text = history.datetime
            historyShortDescription.text = history.shortDesc
            historyVisitedAt.text = StringBuilder(historyVisitedAt.text)
                    .append(": ")
                    .append(history.visitedAt).toString()
            historyTitle.text = history.title
        }
    }
}