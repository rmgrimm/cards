package rmg.apps.cards.backend

import rmg.apps.cards.base.PagedArray

class InMemoryPagedArray<T>(
    override val currentPage: Int,
    override val pageSize: Int,
    val array: Array<T>
) : PagedArray<T> {
    override val pageValues: Array<T>
        get() = array.copyOfRange(currentPage * pageSize, (currentPage + 1) * pageSize - 1)

    override val totalItems = array.size

    override fun getPage(pageNumber: Int): PagedArray<T> {
        if (pageNumber !in 0..lastPage) {
            throw IllegalArgumentException("Page $pageNumber is not between 0 and $lastPage")
        }

        return InMemoryPagedArray(pageNumber, pageSize, array)
    }

}
