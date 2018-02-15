package dv.serg.lentanews.abstr

interface ParentView<in T> {
    fun showData(data: List<T>)
    fun showError(errorMessage: String)
    fun onComplete()
}