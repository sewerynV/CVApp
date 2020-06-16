package com.seweryn.cvapp.ui.list.experience

import com.seweryn.cvapp.data.model.Experience
import com.seweryn.cvapp.ui.list.BaseDiffUtilCallback

class ExperienceDiffUtilCallback(
    private val newList: List<Experience>,
    private val oldList: List<Experience>
) : BaseDiffUtilCallback<Experience>(newList, oldList) {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].id == oldList[oldItemPosition].id
    }
}