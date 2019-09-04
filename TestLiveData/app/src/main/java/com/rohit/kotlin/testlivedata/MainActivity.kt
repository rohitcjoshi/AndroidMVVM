package com.rohit.kotlin.testlivedata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rohit.kotlin.testlivedata.data.Word
import com.rohit.kotlin.testlivedata.viewmodels.WordViewModel
import com.rohit.kotlin.testlivedata.views.NewWordActivity
import com.rohit.kotlin.testlivedata.views.WordListAdapter

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener { view ->
            val newWordIntent = Intent(this, NewWordActivity::class.java)
            startActivityForResult(newWordIntent, newWordActivityRequestCode)
        }

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        wordViewModel.allWords.observe(this, Observer {
            words -> words?.let { adapter.setWordsList(it) }
        })
    }

    companion object {
        const val newWordActivityRequestCode = 10
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == newWordActivityRequestCode) {
            if(resultCode == Activity.RESULT_OK) {
                data?.let {
                    val word = Word(0, it.getStringExtra(NewWordActivity.REPLY_INTENT_PARAM)!!)
                    wordViewModel.insertWord(word)
                }
            } else {
                Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
