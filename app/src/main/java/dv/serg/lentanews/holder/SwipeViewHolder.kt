package dv.serg.lentanews.holder

import android.support.annotation.IntDef
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View

abstract class SwipeViewHolder(view: View, @Direction val direction: Int, swipeListener: (swipePos: Int) -> Unit = {}) : RecyclerView.ViewHolder(view) {

    companion object {
        @Retention(AnnotationRetention.SOURCE)
        @IntDef(RIGHT.toLong(), LEFT.toLong())
        annotation class Direction

        const val RIGHT = ItemTouchHelper.RIGHT
        const val LEFT = ItemTouchHelper.LEFT
    }

    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, direction) {
        override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
            swipeListener.invoke(viewHolder?.adapterPosition ?: 0)
        }
    }

    private val touchHelper: ItemTouchHelper = ItemTouchHelper(itemTouchCallback)

    fun attachToRecyclerView(recyclerView: RecyclerView) {
        touchHelper.attachToRecyclerView(recyclerView)
    }

}