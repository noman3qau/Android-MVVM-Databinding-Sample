package com.noman.mvvmdb.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.noman.mvvmdb.service.model.Project
import com.noman.mvvmdb.service.repository.ProjectRepository

class ProjectListViewModel(application: Application) : ViewModel() {

    var projectListAbservable: LiveData<List<Project>>

    init {
        // If any Transform needed this can be done by transforamtion class..
        projectListAbservable = ProjectRepository.getInstance().getPeojectList("Google")
    }

    /**
     * Expoase live project data query so that ui can abserve
     */
    fun getProjectListObservable(): LiveData<List<Project>> {
        return projectListAbservable
    }

    /**
     * A creator is used to inject project id in to view model
     */
    class Factory(var application: Application) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            return ProjectListViewModel(application) as T
        }
    }

}