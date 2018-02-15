package dv.serg.lentanews.dao.abstr

import io.reactivex.Flowable
import io.reactivex.Maybe

interface Dao<in K, V> {
    operator fun get(id: K): Maybe<V?>
    fun getAll(): Flowable<List<V>>

    fun insert(value: V)
    fun insertAll(value: List<V>)

    fun deleteAll()
    fun delete(value: V)

    fun update(value: V)
}