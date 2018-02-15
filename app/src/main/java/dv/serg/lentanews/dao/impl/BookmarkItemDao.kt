package dv.serg.lentanews.dao.impl

import android.arch.persistence.room.Room
import android.content.Context
import dv.serg.lentanews.dao.abstr.Dao
import dv.serg.lentanews.dao.room.SourceItemDatabase
import dv.serg.lentanews.dao.room.dao.BookmarkItemRoomDao
import dv.serg.lentanews.dao.room.entity.Bookmark
import io.reactivex.Flowable
import io.reactivex.Maybe
import org.jetbrains.anko.doAsync

class BookmarkItemDao(private val context: Context) : Dao<Bookmark, Bookmark> {

    companion object {
        private const val DATABASE_NAME = "app_database"
    }

    private val dao: BookmarkItemRoomDao = Room.databaseBuilder(context, SourceItemDatabase::class.java, DATABASE_NAME).build().getBookmarkDao()

    override fun get(id: Bookmark): Maybe<Bookmark?> {
        return dao.get(id.getKey())
    }

    override fun getAll(): Flowable<List<Bookmark>> {
        return dao.getAll()
    }

    override fun insert(value: Bookmark) {
            dao.insert(value)
    }

    override fun delete(value: Bookmark) {
            dao.delete(value)
    }

    override fun update(value: Bookmark) {
            dao.update(value)
    }

    override fun insertAll(value: List<Bookmark>) {
            dao.insertAll(value)
    }

    override fun deleteAll() {
            dao.deleteAll()
    }
}