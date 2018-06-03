package com.noman.mvvmdb.view.ui.fragments

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.noman.mvvmdb.R
import com.noman.mvvmdb.databinding.FragmentLoginBinding
import com.noman.mvvmdb.view.callback.LoginClickListener
import com.noman.mvvmdb.view.ui.MainActivity
import com.noman.mvvmdb.viewmodel.LoginViewModel

class LoginFragment : Fragment(), LifecycleOwner {

    companion object {
        val TAG: String = "Login Page"
    }

    lateinit var binding: FragmentLoginBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = LoginViewModel.Factory(activity!!.application, loginClickListner)

        var loginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)

        binding.loginViewModelXml = loginViewModel
    }


    private val loginClickListner = object : LoginClickListener {
        override fun onLoginClick(check: Boolean) {
            if (check) {
                (activity as MainActivity).goToProjectListPage()
            } else {
                Toast.makeText(activity, "Email or password are incorrect!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
