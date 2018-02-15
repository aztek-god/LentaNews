package dv.serg.lentanews.dao.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import dv.serg.lentanews.dao.abstr.SpecificKey
import dv.serg.lentanews.util.getCurrentDatetime

@Entity(tableName = "bookmark")
data class Bookmark(
        @ColumnInfo(name = "source_from")
        val sourceName: String,
        @ColumnInfo(name = "header")
        val title: String,
        @ColumnInfo(name = "short_desc")
        val shortDesc: String,
        @ColumnInfo(name = "url")
        val url: String,
        @ColumnInfo(name = "added_at")
        val addedAt: String = getCurrentDatetime()
) : SpecificKey<String> {

    override fun getKey(): String {
        return addedAt + title.substring(0, 10)
    }

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    var specKey: String = getKey()
}