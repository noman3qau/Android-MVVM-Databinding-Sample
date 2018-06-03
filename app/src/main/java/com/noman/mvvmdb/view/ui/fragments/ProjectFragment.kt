package com.noman.mvvmdb.view.ui

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.arch.lifecycle.ViewModelProviders
import com.noman.mvvmdb.R
import com.noman.mvvmdb.databinding.FragmentProjectDetailsBinding
import com.noman.mvvmdb.viewmodel.ProjectViewModel


class ProjectFragment : Fragment(), LifecycleOwner {

    lateinit var binding: FragmentProjectDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_details, container, false)

        // Create and set adapter for RecyclerView
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = ProjectViewModel.Factory(
                activity!!.application, arguments!!.getString(ProjectFragment.KEY_PROJECT_ID)!!)

        val viewModel = ViewModelProviders.of(this, factory)
                .get(ProjectViewModel::class.java)

        binding.projectViewModel = viewModel
        binding.isLoading = true

        observeViewModel(viewModel)

    }


    private fun observeViewModel(viewModel: ProjectViewModel) {
        // Observe project data
        viewModel.getObservableProject().observe(this, Observer { project ->
            if (project != null) {
                binding.isLoading = false
                viewModel.setProject(project)
            }
        })
    }

    companion object {
        val KEY_PROJECT_ID = "project_id"
        /** Creates project fragment for specific project ID  */
        fun forProject(projectID: String): ProjectFragment {
            val fragment = ProjectFragment()
            val args = Bundle()

            args.putString(KEY_PROJECT_ID, projectID)
            fragment.arguments = args

            return fragment
        }
    }

}