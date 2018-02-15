package dv.serg.lentanews.presenter

import dv.serg.lentanews.dao.abstr.Dao
import dv.serg.lentanews.dao.room.entity.SourceItem

class SourcePresenter(val dao: Dao<Int, SourceItem>) {
    fun save(sourceItem: SourceItem) {
        dao.insert(sourceItem)
    }

    fun saveAll(items: List<SourceItem>) {
        items.forEach { dao.insert(it) }
    }

    fun delete(item: SourceItem) {
        dao.delete(item)
    }
}