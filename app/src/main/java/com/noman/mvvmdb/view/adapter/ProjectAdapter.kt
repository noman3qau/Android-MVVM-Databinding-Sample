package com.noman.mvvmdb.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import java.util.zip.Inflater
import android.view.LayoutInflater
import android.databinding.DataBindingUtil
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.util.DiffUtil
import com.noman.mvvmdb.R
import com.noman.mvvmdb.databinding.ProjectListItemBinding
import com.noman.mvvmdb.service.model.Project
import com.noman.mvvmdb.view.callback.ProjectClickCallback
import java.util.*


class ProjectAdapter(var projectClickCallBack: ProjectClickCallback) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {

        val binding: ProjectListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent?.getContext()), R.layout.project_list_item,
                parent, false)

        binding.callback = projectClickCallBack

        return ProjectViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {

        holder?.binding?.project = this.projectListItems?.get(position)
        holder?.binding?.executePendingBindings();

    }


    var projectListItems: List<Project>? = null

    override fun getItemCount(): Int {
        return projectListItems!!.size
    }

    /**
     * Project listview adapter view holder
     */
    class ProjectViewHolder(var bindingg: ProjectListItemBinding) : RecyclerView.ViewHolder(bindingg.root) {
        var binding: ProjectListItemBinding

        init {
            binding = bindingg
        }
    }


    fun setPrjectList(projectList: List<Project>) {
        if (this.projectListItems == null) {
            this.projectListItems = projectList
            notifyItemRangeInserted(0, projectList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@ProjectAdapter.projectListItems!!.size
                }

                override fun getNewListSize(): Int {
                    return projectList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@ProjectAdapter.projectListItems?.get(oldItemPosition)?.id === projectList[newItemPosition].id
                }

                @RequiresApi(Build.VERSION_CODES.KITKAT)
                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

                    var project: Project = projectList.get(newItemPosition)

                    var old: Project = projectList.get(oldItemPosition)

                    return project.id == old.id && Objects.equals(project.git_url, old.git_url)

                }
            })
            this.projectListItems = projectList
            result.dispatchUpdatesTo(this)
        }
    }


}