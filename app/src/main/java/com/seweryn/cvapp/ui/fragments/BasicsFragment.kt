package com.seweryn.cvapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.seweryn.cvapp.R
import com.seweryn.cvapp.ui.extensions.hide
import com.seweryn.cvapp.ui.extensions.show
import com.seweryn.cvapp.ui.extensions.showConditionally
import com.seweryn.cvapp.utils.ActionHelper
import com.seweryn.cvapp.viewmodel.sections.BasicsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_basics.*
import kotlinx.android.synthetic.main.fragment_basics.view.*

class BasicsFragment : BaseFragment<BasicsViewModel>() {

    companion object {
        val TAG = "basics"
    }

    override fun viewModel() =
        ViewModelProvider(this, viewModelFactory).get(BasicsViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_basics, container, false)
        view.phone.setOnClickListener {
            context?.let {
                ActionHelper.makePhoneCall(it, view.phone.text.toString())
            }
        }
        view.email.setOnClickListener {
            context?.let {
                ActionHelper.sendEmail(it, view.email.text.toString())
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeBasics()
        observeError()
        observeProgress()
    }

    private fun observeProgress() {
        viewModel.progress.observe(viewLifecycleOwner, Observer {
            basics_progress.showConditionally(it)
        })
    }

    private fun observeError() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it == null) basics_error_view.hide()
            else {
                basics_error_view.setError(it)
                basics_error_view.show()
            }
        })
    }

    private fun observeBasics() {
        viewModel.content.observe(viewLifecycleOwner, Observer {
            position.text = it.position
            name_surname.text = "${it.name} ${it.surname}"
            phone.text = it.phone
            email.text = it.email
            Picasso.get().load(it.photoUrl)
                .error(R.drawable.ic_photo_placeholder)
                .placeholder(R.drawable.image_progress_animation)
                .into(picture)
        })
    }

}