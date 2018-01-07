package dv.serg.lentanews.adapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dv.serg.lentanews.R
import dv.serg.lentanews.pojo.History

class HistoryAdapter(private val mData: MutableList<History> = ArrayList(), private val listener: () -> Unit = {})
    : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.history_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(mData[position], listener)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val historyTile: ConstraintLayout = view.findViewById(R.id.historyTile)
        private val historySourceFrom: TextView = view.findViewById(R.id.historySourceFrom)
        private val historyDatetime: TextView = view.findViewById(R.id.historyDatetime)
        private val historyShortDescription: TextView = view.findViewById(R.id.historyShortDescription)
        private val historyVisitedAt: TextView = view.findViewById(R.id.historyVisitedAt)
        private val historyTitle: TextView = view.findViewById(R.id.historyTitle)

        fun bind(history: History, listener: () -> Unit) {
            historyTile.setOnClickListener {
                listener.invoke()
            }

            historySourceFrom.text = history.sourceFrom
            historyDatetime.text = history.dateTime
            historyShortDescription.text = history.shortDesc
            historyVisitedAt.text = history.visited
            historyTitle.text = history.title
        }
    }
}