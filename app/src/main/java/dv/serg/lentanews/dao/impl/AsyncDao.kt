package dv.serg.lentanews.dao.impl

import dv.serg.lentanews.dao.abstr.Dao
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AsyncDao<in K, V>(private val dao: Dao<K, V>) : Dao<K, V> by dao {

    var preMainThreadAction: () -> Unit = {}
    var preAsyncAction: () -> Unit = {}
    var uiAction: () -> Unit = {}

    override fun insert(value: V) {
        preMainThreadAction.invoke()
        doAsync {
            preAsyncAction.invoke()
            dao.insert(value)
            uiThread {
                uiAction.invoke()
            }
        }
    }

    override fun insertAll(value: List<V>) {
        preMainThreadAction.invoke()
        doAsync {
            preAsyncAction.invoke()
            dao.insertAll(value)
            uiThread {
                uiAction.invoke()
            }
        }
    }

    override fun deleteAll() {
        preMainThreadAction.invoke()
        doAsync {
            preAsyncAction.invoke()
            dao.deleteAll()
            uiThread {
                uiAction.invoke()
            }
        }
    }

    override fun delete(value: V) {
        preMainThreadAction.invoke()
        doAsync {
            preAsyncAction.invoke()
            dao.delete(value)
            uiThread {
                uiAction.invoke()
            }
        }
    }

    override fun update(value: V) {
        preMainThreadAction.invoke()
        doAsync {
            preAsyncAction.invoke()
            dao.update(value)
            uiThread {
                uiAction.invoke()
            }
        }
    }
}