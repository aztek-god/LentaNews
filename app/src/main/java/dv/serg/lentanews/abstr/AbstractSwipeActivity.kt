package dv.serg.lentanews.abstr

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

abstract class AbstractSwipeActivity: AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView

    private var mSwipeDirs: Int = ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)

    abstract fun initRecycler(): RecyclerView

    abstract fun handleSwipeAction(position: Int?)

    private val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, mSwipeDirs) {
        override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
            handleSwipeAction(viewHolder?.adapterPosition)
        }
    }

    fun setSwipeDirection(dirs: Int) {
        mSwipeDirs = dirs
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mRecyclerView = initRecycler()

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
    }
}