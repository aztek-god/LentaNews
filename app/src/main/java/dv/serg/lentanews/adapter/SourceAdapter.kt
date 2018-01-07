package dv.serg.lentanews.adapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import dv.serg.lentanews.R
import dv.serg.lentanews.pojo.SourceItem
import dv.serg.lentanews.pojo.getPredefinedSources
import kotlinx.android.synthetic.main.source_item_layout.view.*
import timber.log.Timber
import java.util.HashSet
import kotlin.collections.HashMap

typealias Tim = Timber

class SourceAdapter(private val mData: List<SourceItem> = getPredefinedSources(), val actionListener: ActionListener)
    : RecyclerView.Adapter<SourceAdapter.ViewHolder>() {

    interface ActionListener {
        fun action(selectedCount: Int)
    }

    private val checkedMap: MutableMap<Int, Boolean> = HashMap()

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        Tim.d("onBindViewHolder:position = $position")
        holder?.bind(mData[position]) { it: View ->
            with(it.sourceCheckBox) {
                if (!isChecked) {
                    isChecked = true
                    checkedMap.put(position, true)
                } else {
                    isChecked = false
                    checkedMap.put(position, false)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        Tim.d("onCreateViewHolder")

        val view = LayoutInflater.from(parent?.context).inflate(R.layout.source_item_layout, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
//        Tim.d("getItemCount")
        return mData.size
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    fun getSelectedSources(): List<SourceItem> {
        Tim.d("SourceAdapter:getSelectedSources:checkedMap = $checkedMap")
        Tim.d("SourceAdapter:getSelectedSources:mData = $mData")

        val sources: MutableList<SourceItem> = ArrayList()

        for((key: Int, value: Boolean) in checkedMap) {
            if(value) {
                sources.add(mData[key])
            }
        }

        Tim.d("SourceAdapter:getSelectedSources:sources = $sources")

        val filteredList = mData.filterIndexed { index: Int, _: SourceItem ->
            checkedMap[index] == true
        }

        Tim.d("SourceAdapter:getSelectedSources:filteredList = $filteredList")

        return filteredList
    }

    fun clear() {
        Tim.d("SourceAdapter:clear")
        checkedMap.clear()
        checkBoxes.filter { it != null }.forEach { it?.isChecked = false }
    }

    // todo dirty code this field is designed to clear checkboxes as it were designed
    val checkBoxes: MutableSet<CheckBox?> = HashSet()

    inner class ViewHolder(val view: View?) : RecyclerView.ViewHolder(view) {
        private val sourceTile: ConstraintLayout? = view?.findViewById(R.id.sourceTile)
        private val sourceTitle: TextView? = view?.findViewById(R.id.sourceTitle)
        private val sourceDesc: TextView? = view?.findViewById(R.id.sourceDesc)
        private val sourceThumbnail: ImageView? = view?.findViewById(R.id.sourceThumbnail)
        private val sourceCheckBox: CheckBox? = view?.findViewById(R.id.sourceCheckBox)


        fun bind(item: SourceItem, listener: (view: View) -> Unit = {}) {
            sourceTitle?.text = item.sourceTitle
            sourceDesc?.text = item.sourceDesc

            checkBoxes.add(sourceCheckBox)

            val resources = view?.context?.resources

            sourceTile?.setOnClickListener {
                listener.invoke(sourceTile)
                actionListener.action(getSelectedSources().size)


                Tim.d("checkedMap = $checkedMap")
            }

            val identifier = resources?.
                    getIdentifier(item.sourceThumbnailDescriptor,
                            "drawable", view?.context?.packageName)

            sourceThumbnail?.setImageResource(identifier ?: 0)
        }
    }
}