package dv.serg.lentanews.dao.impl

import android.arch.persistence.room.Room
import android.content.Context
import dv.serg.lentanews.dao.abstr.Dao
import dv.serg.lentanews.dao.room.SourceItemDatabase
import dv.serg.lentanews.dao.room.dao.SourceItemRoomDao
import dv.serg.lentanews.dao.room.entity.SourceItem
import io.reactivex.Flowable
import io.reactivex.Maybe

class SourceItemDao(context: Context) : Dao<Int, SourceItem> {

    companion object {
        private const val DATABASE_NAME = "app_database"
    }

    private val dao: SourceItemRoomDao = Room.databaseBuilder(context, SourceItemDatabase::class.java, DATABASE_NAME).build().getSourceItemDao()

    override fun get(id: Int): Maybe<SourceItem?> {
        return dao.get(id)
    }

    override fun getAll(): Flowable<List<SourceItem>> {
        return dao.getAll()
    }

    override fun insert(value: SourceItem) {
        dao.insert(value)
    }

    override fun delete(value: SourceItem) {
        dao.delete(value)
    }

    override fun update(value: SourceItem) {
        dao.update(value)
    }

    override fun insertAll(value: List<SourceItem>) {
        dao.insertAll(value)
    }

    override fun deleteAll() {
        dao.deleteAll()
    }

}