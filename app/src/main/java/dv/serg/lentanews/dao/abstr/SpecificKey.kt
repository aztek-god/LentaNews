package dv.serg.lentanews.dao.abstr

interface SpecificKey<T : Comparable<T>> {
    fun getKey(): T
}