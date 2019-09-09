package com.rohit.kotlin.testdatabinding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class UserModel(): BaseObservable() {
    @Bindable
    var userName: String = String()
    set(value) {
        field = value
        notifyPropertyChanged(BR.userName)
    }
    get() = field

    @Bindable
    var password: String = String()
    set(value) {
        field = value
        notifyPropertyChanged(BR.password)
    }

    companion object {
        fun create(uName: String, pass: String): UserModel {
            var userModel = UserModel()
            userModel.userName = uName
            userModel.password = pass
            return userModel
        }
    }
}