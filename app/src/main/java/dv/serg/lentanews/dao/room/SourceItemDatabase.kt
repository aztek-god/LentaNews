package dv.serg.lentanews.dao.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import dv.serg.lentanews.dao.room.dao.BookmarkItemRoomDao
import dv.serg.lentanews.dao.room.dao.HistoryItemRoomDao
import dv.serg.lentanews.dao.room.dao.SourceItemRoomDao
import dv.serg.lentanews.dao.room.entity.Bookmark
import dv.serg.lentanews.dao.room.entity.History
import dv.serg.lentanews.dao.room.entity.SourceItem

@Database(entities = arrayOf(SourceItem::class, History::class, Bookmark::class), version = 1)
abstract class SourceItemDatabase : RoomDatabase() {
    abstract fun getSourceItemDao(): SourceItemRoomDao
    abstract fun getHistoryDao(): HistoryItemRoomDao
    abstract fun getBookmarkDao(): BookmarkItemRoomDao
}