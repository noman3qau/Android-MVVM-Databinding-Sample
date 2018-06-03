package com.noman.mvvmdb.view.callback

import com.noman.mvvmdb.service.model.Project


interface ProjectClickCallback {
    fun onClick(poject: Project)
}