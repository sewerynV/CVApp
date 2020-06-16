package com.seweryn.cvapp.ui.list.skill

import com.seweryn.cvapp.data.model.Skill
import com.seweryn.cvapp.ui.list.BaseDiffUtilCallback

class SkillDiffUtilCallback(
    private val newList: List<Skill>,
    private val oldList: List<Skill>
) : BaseDiffUtilCallback<Skill>(newList, oldList) {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].id == oldList[oldItemPosition].id
    }
}