package dv.serg.lentanews.dao.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import dv.serg.lentanews.dao.abstr.SpecificKey
import dv.serg.lentanews.util.getCurrentDatetime

@Entity(tableName = "history")
data class History(
        @ColumnInfo(name = "title")
        val title: String,
        @ColumnInfo(name = "source_name")
        val sourceName: String,
        @ColumnInfo(name = "short_desc")
        val shortDesc: String,
        @ColumnInfo(name = "datetime")
        val datetime: String,
        @ColumnInfo(name = "url")
        val url: String,
        @ColumnInfo(name = "visited_at")
        val visitedAt: String = getCurrentDatetime()
) : SpecificKey<String> {

    override fun getKey(): String {
        return visitedAt + title.substring(0, 10)
    }

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    var specKey: String = getKey()
}