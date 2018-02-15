package dv.serg.lentanews.dao.room.dao

import android.arch.persistence.room.*
import dv.serg.lentanews.dao.room.entity.History
import dv.serg.lentanews.dao.room.entity.SourceItem
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
abstract class HistoryItemRoomDao {
    @Query("SELECT * FROM history")
    abstract fun getAll(): Flowable<List<History>>

    @Query("SELECT * FROM history WHERE id = :spec_key LIMIT 1")
    abstract fun get(spec_key: String): Maybe<History?>

    @Insert
    abstract fun insert(sourceItem: History)

    @Update
    abstract fun update(sourceItem: History)

    @Delete
    abstract fun delete(sourceItem: History)

    @Query("DELETE FROM history")
    abstract fun deleteAll()

    @Transaction
    open fun insertAll(entities: List<History>) {
        entities.forEach { insert(it) }
    }
}