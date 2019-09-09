package com.rohit.kotlin.testdatabinding.data

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class UserModel(): BaseObservable() {
    @Bindable
    var firstName: String = String()
    set(value) {
        field = value
        notifyPropertyChanged(BR.firstName)
    }
    get() = field

    @Bindable
    var lastName: String = String()
    set(value) {
        field = value
        notifyPropertyChanged(BR.lastName)
    }

    companion object {
        fun create(uName: String, pass: String): UserModel {
            var userModel = UserModel()
            userModel.firstName = uName
            userModel.lastName = pass
            return userModel
        }
    }
}