package com.seweryn.cvapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.seweryn.cvapp.R
import com.seweryn.cvapp.ui.extensions.hide
import com.seweryn.cvapp.ui.extensions.show
import com.seweryn.cvapp.ui.extensions.showConditionally
import com.seweryn.cvapp.ui.fragments.BasicsFragment
import com.seweryn.cvapp.ui.fragments.EducationFragment
import com.seweryn.cvapp.ui.fragments.ExperienceFragment
import com.seweryn.cvapp.ui.fragments.SkillsFragment
import com.seweryn.cvapp.viewmodel.MainViewModel
import com.seweryn.cvapp.viewmodel.MainViewModel.InitializationState.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) initializeFragments()
        initializeBottomNavigationView()
        AndroidInjection.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        observeState()
        observeSecondaryProgress()
    }

    private fun initializeBottomNavigationView() {
        bottom_navigation_view.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_basics -> showFragment(BasicsFragment.TAG)
                R.id.action_skills -> showFragment(SkillsFragment.TAG)
                R.id.action_experience -> showFragment(ExperienceFragment.TAG)
                R.id.action_education -> showFragment(EducationFragment.TAG)
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun initializeFragments() {
        initializeFragment(BasicsFragment(), BasicsFragment.TAG, true)
        initializeFragment(SkillsFragment(), SkillsFragment.TAG, false)
        initializeFragment(ExperienceFragment(), ExperienceFragment.TAG, false)
        initializeFragment(EducationFragment(), EducationFragment.TAG, false)
    }

    private fun initializeFragment(fragment: Fragment, tag: String, isMainFragment: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, fragment, tag)
            if (!isMainFragment) hide(fragment)
            commit()
        }
    }

    private fun showFragment(fragmentTag: String) {
        supportFragmentManager.apply {
            findFragmentByTag(fragmentTag)?.let { fragment ->
                fragments.forEach {
                    beginTransaction().hide(it).commit()
                }
                beginTransaction().show(fragment).commit()
            }
        }
    }

    private fun observeState() {
        viewModel.initializationState.observe(this, Observer { state ->
            when (state) {
                is Progress -> {
                    initialization_error_view.hide()
                    bottom_navigation_view.hide()
                    initialization_progress_view.show()
                }
                is Ready -> {
                    initialization_error_view.hide()
                    initialization_progress_view.hide()
                    bottom_navigation_view.show()
                }
                is Failure -> {
                    initialization_progress_view.hide()
                    bottom_navigation_view.hide()

                    initialization_error_view.setError(state.error)
                    initialization_error_view.show()
                }
            }
        })
    }

    private fun observeSecondaryProgress() {
        viewModel.secondaryProgress.observe(this, Observer {
            secondary_progress.showConditionally(it)
        })
    }
}
