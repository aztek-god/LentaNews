package dv.serg.lentanews.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dv.serg.lentanews.ui.adapter.abstr.AbstractAdapter

class CommonAdapter<T, VH>(private val recyclerItemId: Int, private val viewHolderSelector: (View) -> VH,
                           override var oldData: List<T> = ArrayList())
    : AbstractAdapter<T, VH>() where VH : RecyclerView.ViewHolder, VH : CommonAdapter.ViewHolderBindable<T> {

    private var clickListener: (Int) -> Unit = {}
    private var longClickListener: (Int) -> Unit = {}

    fun applyClickListener(clickListener: (Int) -> Unit) {
        this.clickListener = clickListener
    }

    fun applyLongClickListener(longClickListener: (Int) -> Unit) {
        this.longClickListener = longClickListener
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(oldData[position], clickListener, longClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH {
        val view: View = LayoutInflater.from(parent?.context).inflate(recyclerItemId, parent, false)

        return viewHolderSelector.invoke(view)
    }

    override fun getItemCount(): Int {
        return oldData.size
    }

    interface ViewHolderBindable<T> {
        fun bind(item: T, clickListener: (Int) -> Unit, longClickListener: (Int) -> Unit)
    }
}