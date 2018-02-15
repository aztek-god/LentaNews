package dv.serg.lentanews.adapter.abstr

import android.support.v7.widget.RecyclerView

abstract class StableIdAdapter<T : RecyclerView.ViewHolder> : RecyclerView.Adapter<T>() {
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position
}