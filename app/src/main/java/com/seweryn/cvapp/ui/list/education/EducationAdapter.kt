package com.seweryn.cvapp.ui.list.education

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seweryn.cvapp.R
import com.seweryn.cvapp.data.model.Education
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_education.view.*

class EducationAdapter : RecyclerView.Adapter<EducationAdapter.ViewHolder>() {

    private var education = listOf<Education>()

    fun setEducation(education: List<Education>) {
        val diff = DiffUtil.calculateDiff(
            EducationDiffUtilCallback(
                education,
                this.education
            )
        )
        this.education = education
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_education, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = education.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(education[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(education: Education) {
            itemView.school_name.text = education.schoolName
            itemView.degree.text = education.degree
            itemView.study_field.text = education.specialization
            itemView.start_date.text = education.startDate
            itemView.end_date.text = education.endDate
            Picasso.get().load(education.schoolIconUrl).into(itemView.school_icon)
        }
    }
}