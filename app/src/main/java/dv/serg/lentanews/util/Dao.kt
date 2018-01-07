package dv.serg.lentanews.util

interface ReadOnlyDao<K, V> {
    fun getById(id: K): String?

    fun getAll(): List<V>
}

interface CrudDao<K, V> : ReadOnlyDao<K, V> {
    fun save(item: V): Boolean

    fun update(item: V): Boolean

    fun delete(item: V): Boolean

    fun deleteById(id: K): Boolean

    fun deleteAll(): Boolean
}