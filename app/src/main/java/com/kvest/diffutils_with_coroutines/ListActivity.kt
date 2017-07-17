package com.kvest.diffutils_with_coroutines

import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

/**
 * Created by kvest on 7/11/2017.
 */
private const val LIST_ITEMS_COUNT = 10_000
private const val ITEMS_COUNT_TO_SHUFFLE = 10

class ListActivity : AppCompatActivity() {
    val adapter by lazy { ListAdapter() }
    private lateinit var animator: ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        initList()
    }

    override fun onStart() {
        super.onStart()

        animator = ObjectAnimator.ofFloat(uiLugIndicator, View.ROTATION_Y, 360f)
        animator.duration = 2000
        animator.repeatCount = Animation.INFINITE
        animator.start()
    }

    override fun onStop() {
        animator.end()

        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_activity_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_first_item -> deleteFirstItem()
            R.id.update_names -> updateNames()
            R.id.shuffle_items -> shuffleItems()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initList() {
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        launch(UI) {
            val items = provideItems().await()
            adapter.items = items
        }
    }

    private fun deleteFirstItem() {
        val newItems = adapter.items.drop(1)
        adapter.items = newItems
    }

    private fun updateNames() {
        launch(UI) {
            val newItems = async(CommonPool) {
                adapter.items.map { item -> item.copy(name = "Item ${System.nanoTime() % LIST_ITEMS_COUNT}") }
            }.await()

            adapter.items = newItems
        }
    }

    private fun shuffleItems() {
        launch(UI){
            val newItems = async(CommonPool) {
                val newItems = adapter.items.toMutableList()
                for (i in 0 until Math.min(ITEMS_COUNT_TO_SHUFFLE, newItems.size)) {
                    newItems.swap(i, newItems.size -1 - i)
                }

                newItems
            }.await()

            adapter.items = newItems
        }
    }

    private suspend fun provideItems() = async(CommonPool) {
        List(LIST_ITEMS_COUNT) {
            i -> Item(i, "Item ${System.nanoTime() % LIST_ITEMS_COUNT}")
        }
    }
}
