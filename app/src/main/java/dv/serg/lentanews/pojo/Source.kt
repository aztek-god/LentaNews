package dv.serg.lentanews.pojo

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

@Entity(tableName = "source")
data class Source(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val orderId: Int,
        @ColumnInfo(name = "code")
        @SerializedName("id")
        @Expose
        val id: String?,
        @ColumnInfo(name = "name")
        @SerializedName("name")
        @Expose
        val name: String?
)