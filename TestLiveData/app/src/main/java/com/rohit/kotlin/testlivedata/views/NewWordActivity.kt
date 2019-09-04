package com.rohit.kotlin.testlivedata.views

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import com.rohit.kotlin.testlivedata.R
import kotlinx.android.synthetic.main.activity_new_word.*

class NewWordActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText

    companion object {
        const val REPLY_INTENT_PARAM = "NEW_WORD_ENTRY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        editWordView = edit_word

        val button = button_save
        button.setOnClickListener {
            val replyIntent = Intent()
            if(TextUtils.isEmpty(editWordView.text)) {
                Toast.makeText(this, "Enter some text..! Word cannot be empty..!", Toast.LENGTH_LONG).show()
            } else {
                val word = editWordView.text.toString()
                replyIntent.putExtra(REPLY_INTENT_PARAM, word)
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED, Intent())
        finish()
    }
}
