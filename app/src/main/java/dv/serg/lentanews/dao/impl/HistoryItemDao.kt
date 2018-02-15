package dv.serg.lentanews.dao.impl

import android.arch.persistence.room.Room
import android.content.Context
import dv.serg.lentanews.dao.abstr.Dao
import dv.serg.lentanews.dao.room.SourceItemDatabase
import dv.serg.lentanews.dao.room.dao.HistoryItemRoomDao
import dv.serg.lentanews.dao.room.entity.History
import io.reactivex.Flowable
import io.reactivex.Maybe
import org.jetbrains.anko.doAsync


class HistoryItemDao(context: Context) : Dao<History, History> {

    companion object {
        private const val DATABASE_NAME = "app_database"
    }

    private val dao: HistoryItemRoomDao = Room.databaseBuilder(context, SourceItemDatabase::class.java, DATABASE_NAME).build().getHistoryDao()

    override fun get(id: History): Maybe<History?> {
        return dao.get(id.getKey())
    }

    override fun getAll(): Flowable<List<History>> {
        return dao.getAll()
    }

    override fun insertAll(value: List<History>) {
            dao.insertAll(value)
    }

    override fun deleteAll() {
            dao.deleteAll()
    }

    override fun insert(value: History) {
            dao.insert(value)
    }

    override fun delete(value: History) {
            dao.delete(value)
    }

    override fun update(value: History) {
            dao.update(value)
    }

}