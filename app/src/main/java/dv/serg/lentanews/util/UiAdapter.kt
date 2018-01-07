package dv.serg.lentanews.util

interface UiAdapter<T> {

    fun clear()

    fun updateData(data: List<T>)

    fun appendData(data: List<T>)

    fun removeAt(index: Int)

    fun restoreAt(index: Int, item: T)

    fun notifyDataChanged()

    operator fun get(index: Int): T
}