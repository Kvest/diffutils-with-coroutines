package diffutils_with_coroutines

interface ItemHandler {
    fun onItemSelected(item: Item)
    fun onIdSelected(id: Int)
}