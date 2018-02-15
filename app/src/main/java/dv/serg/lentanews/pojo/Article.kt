package dv.serg.lentanews.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dv.serg.lentanews.dao.abstr.SpecificKey

data class Article(
        @SerializedName("source")
        @Expose
        val source: Source?,
        @SerializedName("author")
        @Expose
        val author: String?,
        @SerializedName("title")
        @Expose
        val title: String?,
        @SerializedName("description")
        @Expose
        val description: String?,
        @SerializedName("url")
        @Expose
        val url: String?,
        @SerializedName("urlToImage")
        @Expose
        val urlToImage: String?,
        @SerializedName("publishedAt")
        @Expose
        val publishedAt: String?
) : SpecificKey<String> {

    override fun getKey(): String {
        return title!!.substring(0, 10) + description!!.substring(0, 10)
    }

    val specKey: String get() = getKey()

    var isBookmarked: Boolean = false
}