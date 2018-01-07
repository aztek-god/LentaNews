package dv.serg.lentanews.contract

interface View<T> {

    fun onError(message: String)

    fun onComplete()

    fun showData(data: T)

}