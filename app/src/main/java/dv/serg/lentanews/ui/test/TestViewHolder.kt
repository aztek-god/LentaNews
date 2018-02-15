package dv.serg.lentanews.ui.test

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import dv.serg.lentanews.R
import dv.serg.lentanews.ui.adapter.CommonAdapter

class TestViewHolder(view: View) : RecyclerView.ViewHolder(view), CommonAdapter.ViewHolderBindable<String> {
    private val stringView = view.findViewById<TextView>(R.id.testTextId)
    override fun bind(item: String, clickListener: (Int) -> Unit, longClickListener: (Int) -> Unit) {
        stringView.text = item
    }
}