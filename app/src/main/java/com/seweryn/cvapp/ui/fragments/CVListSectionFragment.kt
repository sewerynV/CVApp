package com.seweryn.cvapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seweryn.cvapp.R
import com.seweryn.cvapp.ui.extensions.hide
import com.seweryn.cvapp.ui.extensions.show
import com.seweryn.cvapp.ui.extensions.showConditionally
import com.seweryn.cvapp.viewmodel.sections.CVSectionViewModel
import kotlinx.android.synthetic.main.fragment_section_list.*
import kotlinx.android.synthetic.main.fragment_section_list.view.*

abstract class CVListSectionFragment<CONTENT> : BaseFragment<CVSectionViewModel<List<CONTENT>>>() {

    protected abstract val contentAdapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_section_list, container, false)
        view.section_list.adapter = contentAdapter
        view.section_list.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeContent()
        observeError()
        observeProgress()
    }

    private fun observeProgress() {
        viewModel.progress.observe(viewLifecycleOwner, Observer {
            section_progress.showConditionally(it)
        })
    }

    private fun observeError() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it == null) section_error_view.hide()
            else {
                section_error_view.setError(it)
                section_error_view.show()
            }
        })
    }

    private fun observeContent() {
        viewModel.content.observe(viewLifecycleOwner, Observer {
            updateContent(it)
        })
    }

    protected abstract fun updateContent(content: List<CONTENT>)

}