package com.kvest.diffutils_with_coroutines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list.*

/**
 * Created by kvest on 7/11/2017.
 */
private const val LIST_ITEMS_COUNT = 1_000

class ListActivity : AppCompatActivity() {
    val adapter by lazy { ListAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        initList()
    }

    private fun initList() {
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        adapter.items = provideItems()
    }

    private fun provideItems(): List<Item> = List<Item>(LIST_ITEMS_COUNT) {
        i -> Item(i, "Item ${System.nanoTime() % 100_000}")
    }
}
