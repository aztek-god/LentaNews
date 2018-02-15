package dv.serg.lentanews.dao.room.dao

import android.arch.persistence.room.*
import dv.serg.lentanews.dao.room.entity.Bookmark
import dv.serg.lentanews.dao.room.entity.SourceItem
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
abstract class BookmarkItemRoomDao {
    @Query("SELECT * FROM bookmark")
    abstract fun getAll(): Flowable<List<Bookmark>>

    @Query("SELECT * FROM bookmark WHERE id = :spec_key LIMIT 1")
    abstract fun get(spec_key: String): Maybe<Bookmark?>

    @Insert
    abstract fun insert(bookmark: Bookmark)

    @Update
    abstract fun update(bookmark: Bookmark)

    @Delete
    abstract fun delete(bookmark: Bookmark)

    @Query("DELETE FROM bookmark")
    abstract fun deleteAll()

    @Transaction
    open fun insertAll(entities: List<Bookmark>) {
        entities.forEach { insert(it) }
    }
}