package dv.serg.lentanews.dao.room.dao

import android.arch.persistence.room.*
import dv.serg.lentanews.dao.room.entity.SourceItem
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
abstract class SourceItemRoomDao {
    @Query("SELECT * FROM selected_sources")
    abstract fun getAll(): Flowable<List<SourceItem>>

    @Query("SELECT * FROM selected_sources WHERE id = :spec_key LIMIT 1")
    abstract fun get(spec_key: Int): Maybe<SourceItem?>

    @Insert
    abstract fun insert(sourceItem: SourceItem)

    @Update
    abstract fun update(sourceItem: SourceItem)

    @Delete
    abstract fun delete(sourceItem: SourceItem)

    @Query("DELETE FROM selected_sources")
    abstract fun deleteAll()

    @Transaction
    open fun insertAll(entities: List<SourceItem>) {
        entities.forEach { insert(it) }
    }

}