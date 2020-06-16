package com.seweryn.cvapp.ui.list

import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffUtilCallback<T>(
    private val newList: List<T>,
    private val oldList: List<T>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]
        return newItem == oldItem
    }
}