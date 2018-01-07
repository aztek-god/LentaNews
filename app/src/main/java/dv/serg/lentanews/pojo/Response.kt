package dv.serg.lentanews.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Response(
        @SerializedName("status")
        @Expose
        val status: String?,
        @SerializedName("articles")
        @Expose
        val articles: List<Article>?
)