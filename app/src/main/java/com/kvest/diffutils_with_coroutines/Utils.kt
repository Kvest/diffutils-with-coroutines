package com.kvest.diffutils_with_coroutines

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by kvest on 7/12/2017.
 */
fun ViewGroup.inflateWithoutAttach(@LayoutRes layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}
