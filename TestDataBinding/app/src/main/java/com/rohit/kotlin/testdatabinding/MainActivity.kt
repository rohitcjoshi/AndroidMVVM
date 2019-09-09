package com.rohit.kotlin.testdatabinding

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.rohit.kotlin.testdatabinding.data.UserModel
import com.rohit.kotlin.testdatabinding.databinding.TestLayoutBinding


class MainActivity : AppCompatActivity() {

    lateinit var contentBinding: TestLayoutBinding
    lateinit var clickHandlers: ClickHandlers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout)

        clickHandlers = ClickHandlers(this)
        contentBinding = DataBindingUtil.setContentView(this, R.layout.test_layout)
        contentBinding.lifecycleOwner = this

        val userModel = UserModel.create("username", "lastname")

        contentBinding.userModel = userModel
        contentBinding.clickHandler = clickHandlers
    }

    inner class ClickHandlers(context: Context) {
        fun onButtonClick(view: View) {
            Log.d("OnClicked", "Button clicked here ------------>>>>>>>>")

        }
    }
}
