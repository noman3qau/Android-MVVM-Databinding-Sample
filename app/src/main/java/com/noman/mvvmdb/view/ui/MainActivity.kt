package com.noman.mvvmdb.view.ui

import android.arch.lifecycle.LifecycleActivity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.noman.mvvmdb.service.model.Project
import com.noman.mvvmdb.view.ui.fragments.LoginFragment
import com.noman.mvvmdb.R


class MainActivity : LifecycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add project list fragment if this first creation
        if (savedInstanceState == null) {

            val fragment = LoginFragment()

            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment, LoginFragment.TAG).commit()

        }

    }

    /**
     * This fuction shows for project list after login user
     */
    fun goToProjectListPage() {

        val fragment = ProjectListFragment()

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, ProjectListFragment.TAG).commit()

    }


    /** Shows the project detail fragment  */
    fun show(project: Project) {
        val projectFragment = ProjectFragment.forProject(project.name)

        supportFragmentManager.beginTransaction()
                .addToBackStack("project")
                .replace(R.id.fragment_container, projectFragment, null).commit()
    }


}
