package com.seweryn.cvapp.ui.list.education

import com.seweryn.cvapp.data.model.Education
import com.seweryn.cvapp.ui.list.BaseDiffUtilCallback

class EducationDiffUtilCallback(
    private val newList: List<Education>,
    private val oldList: List<Education>
) : BaseDiffUtilCallback<Education>(newList, oldList) {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].id == oldList[oldItemPosition].id
    }
}