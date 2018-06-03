package com.noman.mvvmdb.service.repository

import android.arch.lifecycle.MutableLiveData
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.R.attr.data
import android.arch.lifecycle.LiveData
import android.databinding.adapters.NumberPickerBindingAdapter.setValue
import com.noman.mvvmdb.service.model.Project
import retrofit2.Call
import retrofit2.Response
import java.security.Policy


class ProjectRepository {

    var gitHubService: GitHubService

    companion object {
        var projectRepository: ProjectRepository? = null

        @Synchronized
        fun getInstance(): ProjectRepository {
            if (projectRepository == null) {
                projectRepository = ProjectRepository()
            }
            return projectRepository as ProjectRepository
        }
    }

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(GitHubService.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        gitHubService = retrofit.create(GitHubService::class.java)
    }


    fun getPeojectList(userId: String): MutableLiveData<List<Project>> {

        var data: MutableLiveData<List<Project>> = MutableLiveData<List<Project>>()

        gitHubService.getProjectList(userId).enqueue(object : Callback<List<Project>> {
            override fun onResponse(call: Call<List<Project>>, response: Response<List<Project>>) {
                data.value = response.body()
            }

            override fun onFailure(call: Call<List<Project>>, t: Throwable) {
                // TODO better error handling in part #2 ...
                data.value = null
            }
        })

        return data
    }

    fun getProjectDetails(userId: String, projectName: String): LiveData<Project> {

        var data: MutableLiveData<Project> = MutableLiveData()

        gitHubService.getProjectDetails(userId, projectName).enqueue(object : Callback<Project> {
            override fun onFailure(call: Call<Project>?, t: Throwable?) {
                data.value = null
            }

            override fun onResponse(call: Call<Project>?, response: Response<Project>?) {
                simulateDelay()
                data.value = response?.body()
            }

        })

        return data
    }


    private fun simulateDelay() {
        try {
            Thread.sleep(10)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }
}