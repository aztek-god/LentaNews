package dv.serg.lentanews.pojo

import io.realm.RealmObject

open class History(var sourceFrom: String, var title: String, var shortDesc: String, var dateTime: String,
                   var url: String, var visited: String) : RealmObject() {
    constructor() : this("", "", "", "", "", "")
}

