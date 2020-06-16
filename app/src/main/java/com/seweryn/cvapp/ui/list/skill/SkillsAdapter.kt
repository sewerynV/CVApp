package com.seweryn.cvapp.ui.list.skill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seweryn.cvapp.R
import com.seweryn.cvapp.data.model.Skill
import kotlinx.android.synthetic.main.item_skill.view.*

class SkillsAdapter : RecyclerView.Adapter<SkillsAdapter.ViewHolder>() {

    private var skills: List<Skill> = mutableListOf()

    fun updateSkills(skills: List<Skill>) {
        val diff = DiffUtil.calculateDiff(
            SkillDiffUtilCallback(
                skills,
                this.skills
            )
        )
        this.skills = skills
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_skill, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = skills.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(skills[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(skill: Skill) {
            itemView.skill_name.text = skill.name
            itemView.skill_proficiency.rating = skill.proficiency.toFloat()
        }
    }
}