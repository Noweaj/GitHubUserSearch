package com.noweaj.android.githubusersearch.search

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.*

class SearchViewModel: ViewModel() {

    private val TAG = SearchViewModel::class.java.simpleName
    init{
        Log.d(TAG, "onCreate")
    }

    private var timer: Timer? = null

    val textWatcher = object:TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            timer?.cancel()
            timer = null
        }

        override fun afterTextChanged(s: Editable?) {
            timer = Timer()
            timer!!.schedule(object: TimerTask(){
                override fun run() {
                    // do search
                    Log.d(TAG, s.toString())
                }
            }, 1000)
        }

    }
}