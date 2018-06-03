package com.noman.mvvmdb.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.ObservableField
import com.noman.mvvmdb.service.model.Project
import com.noman.mvvmdb.service.repository.ProjectRepository

class ProjectViewModel(var application: Application, var projectId: String) : ViewModel() {

    var projectObservable: LiveData<Project>

    var project: ObservableField<Project> = ObservableField()


    init {
        projectObservable = ProjectRepository.getInstance().getProjectDetails("Google", projectId)
    }

    fun getObservableProject(): LiveData<Project> {
        return projectObservable
    }

    fun setProject(project: Project) {
        this.project.set(project)
    }

    /**
     * A creator is used to inject project id in to view model
     */
    class Factory(var application: Application, var projectId: String) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return ProjectViewModel(application, projectId) as T
        }
    }

}