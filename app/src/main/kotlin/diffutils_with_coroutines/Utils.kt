package diffutils_with_coroutines

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
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

fun <T> MutableList<T>.swap(i: Int, j: Int) {
    val tmp = this[i]
    this[i] = this[j]
    this[j] = tmp
}

inline fun <reified T : ViewDataBinding> inflateBinding(layoutInflater: LayoutInflater, layoutId: Int, parent: ViewGroup): T {
    return DataBindingUtil.inflate(layoutInflater, layoutId, parent, false)
}