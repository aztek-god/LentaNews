package dv.serg.lentanews.pojo

import io.realm.RealmObject

open class Bookmark(var sourceName: String, var title: String, var desc: String, var url: String, var datetime: String, var publishedAt: String) : RealmObject() {
    constructor() : this(sourceName = "", title = "", desc = "", url = "", datetime = "", publishedAt = "")
}