package com.noman.mvvmdb.viewmodel

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.noman.mvvmdb.service.model.User
import com.noman.mvvmdb.view.callback.LoginClickListener
import java.util.*
import kotlin.math.log

class LoginViewModel(var application: Application, var loginClickListner: LoginClickListener) : ViewModel() {

    var user: User? = null

    init {
        user = User("", 0, "", "", "", "",
                "", "", "", "", "",
                "", "", "", "", "", "",
                "", "", 0, 0, 0, 0, Date(), Date())
    }

    /**
     * A creator is used to inject applicaiton in to view model
     */
    class Factory(var application: Application, var loginClickListner: LoginClickListener) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(application, loginClickListner) as T
        }
    }

    /**
     * Funtion to get email from input field
     */
    fun onEmailChangeListener(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                user?.email = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        }
    }

    /**
     * Function to get password from input field
     */
    fun onPasswordChangeListener(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                user?.name = s.toString()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        }
    }


    fun onLoginClickProcess(view: View) {

        if (!TextUtils.isEmpty(user?.email) && !TextUtils.isEmpty(user?.name)) {
            loginClickListner.onLoginClick(true)
        } else {
            loginClickListner.onLoginClick(false)
        }

    }


}