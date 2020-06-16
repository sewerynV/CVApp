package com.seweryn.cvapp.ui.list.experience

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seweryn.cvapp.R
import com.seweryn.cvapp.data.model.Experience
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_experience.view.*

class ExperienceAdapter : RecyclerView.Adapter<ExperienceAdapter.ViewHolder>() {

    private var experience = listOf<Experience>()

    fun setExperience(experience: List<Experience>) {
        val diff = DiffUtil.calculateDiff(
            ExperienceDiffUtilCallback(
                experience,
                this.experience
            )
        )
        this.experience = experience
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_experience, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = experience.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(experience[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(experience: Experience) {
            itemView.company_name.text = experience.companyName
            itemView.position.text = experience.position
            itemView.start_date.text = experience.startDate
            itemView.end_date.text = experience.endDate
            itemView.description.text = experience.description
            Picasso.get().load(experience.companyIconUrl).into(itemView.company_icon)
        }
    }
}