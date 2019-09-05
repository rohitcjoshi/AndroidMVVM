package com.rohit.kotlin.testdatabinding

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.rohit.kotlin.testdatabinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var contentBinding: ActivityMainBinding
    lateinit var clickHandlers: ClickHandlers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clickHandlers = ClickHandlers(this)
        contentBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val userModel: UserModel = UserModel()
        userModel.userName = "rohit"
        userModel.password = "test123#"

        contentBinding.userModel = userModel
        contentBinding.clickHandler = clickHandlers

        contentBinding.btnLogin.setOnClickListener {

        }
    }

    inner class ClickHandlers(context: Context) {
        fun onButtonClick(view: View) {
            Log.d("OnClicked", "Button clicked here ------------>>>>>>>>")
            contentBinding.tvDataBindingTest.text = contentBinding.etUserName.text.toString()
        }

        fun onOtherButtonClick(view: View) {
            Log.d("OnClicked", "Other Button clicked here ------------>>>>>>>>")
        }
    }
}
