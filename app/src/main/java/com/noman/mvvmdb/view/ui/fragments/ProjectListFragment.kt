package com.noman.mvvmdb.view.ui

import android.arch.lifecycle.LifecycleOwner
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noman.mvvmdb.databinding.FragmentProjectListBinding
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import com.noman.mvvmdb.R
import com.noman.mvvmdb.service.model.Project
import com.noman.mvvmdb.view.adapter.ProjectAdapter
import com.noman.mvvmdb.view.callback.ProjectClickCallback
import com.noman.mvvmdb.viewmodel.ProjectListViewModel


class ProjectListFragment : Fragment(), LifecycleOwner {

    companion object {
        var TAG: String = "ProjectListFragment"
    }

    lateinit var binding: FragmentProjectListBinding

    lateinit var projectAdapter: ProjectAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false)


        projectAdapter = ProjectAdapter(projectClickCallback)
        binding.projectList.adapter = projectAdapter
        binding.isLoading = true


        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = ProjectListViewModel.Factory(activity!!.application)

        val viewModel = ViewModelProviders.of(this, factory).get(ProjectListViewModel::class.java)

        observeViewModel(viewModel)
    }

    private fun observeViewModel(viewModel: ProjectListViewModel) {
        // Update the list when the data changes
        viewModel.getProjectListObservable().observe(this, Observer { projects ->
            if (projects != null) {
                binding.isLoading = false
                projectAdapter.projectListItems = projects
            }
        })
    }

    private val projectClickCallback = object : ProjectClickCallback {
        override fun onClick(project: Project) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as MainActivity).show(project)
            }
        }
    }
}