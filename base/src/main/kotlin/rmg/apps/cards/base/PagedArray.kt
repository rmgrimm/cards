package rmg.apps.cards.base

interface PagedArray<T> {

    val pageValues: Array<T>

    val currentPage: Int
    val pageSize: Int
    val totalItems: Int

    val pageCount: Int
        get() = if (totalItems % pageSize == 0) {
            totalItems / pageSize
        } else {
            totalItems / pageSize + 1
        }
    val firstPage: Int
        get() = 0
    val lastPage: Int
        get() = pageCount - 1

    val hasPreviousPage: Boolean
        get() = currentPage > 0
    val hasNextPage: Boolean
        get() = currentPage < lastPage

    fun getPage(pageNumber: Int): PagedArray<T>
    fun previous(): PagedArray<T> = getPage(currentPage - 1)
    fun next(): PagedArray<T> = getPage(currentPage + 1)
}
