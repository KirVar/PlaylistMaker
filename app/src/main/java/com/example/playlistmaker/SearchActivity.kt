package com.example.playlistmaker


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class SearchActivity : AppCompatActivity() {

    private lateinit var buttonRefresh: Button
    private lateinit var tracksList: RecyclerView
    private lateinit var linearLayoutNothingFound: LinearLayout
    private lateinit var linearLayoutSomethingWentWrong: LinearLayout
    private val tracks = ArrayList<Track>()
    private val adapter = TracksAdapter(tracks)
    private val itunesBaseUrl = "https://itunes.apple.com"



    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val retrofit = Retrofit.Builder()
            .baseUrl(itunesBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val iTunesAPISearchService = retrofit.create(ITunesAPI::class.java)


        linearLayoutNothingFound = findViewById(R.id.nothingFound)
        linearLayoutSomethingWentWrong = findViewById(R.id.somethingWentWrong)
        tracksList = findViewById(R.id.rvTracks)
        buttonRefresh = findViewById(R.id.buttonRefresh)
        tracksList.adapter = adapter

        val buttonBack = findViewById<ImageView>(R.id.arrow_back_light)
        buttonBack.setOnClickListener {
            Intent(this, MainActivity::class.java)
            finish()
        }

        val inputEditText = findViewById<EditText>(R.id.search_input_text)
        if (valueTextInput != "") {
            inputEditText.setText(valueTextInput)
        }

        val clearButton = findViewById<ImageView>(R.id.buttonClearInputText)
        clearButton.setOnClickListener {
            inputEditText.setText("")
            inputEditText.hideKeyboard()
            adapter.notifyDataSetChanged()
            allViewsVisibleGone()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)
                if (savedInstanceState != null) {
                    valueTextInput = savedInstanceState.getString(TEXT_INPUT, DEFAULT_TEXT_INPUT)
                }
                inputEditText.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchSong()
                    }
                    false
                }
                buttonRefresh.setOnClickListener {
                searchSong()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }

            fun searchSong(){
                        if (inputEditText.text.isNotEmpty()) {
                            iTunesAPISearchService.search(inputEditText.text.toString()).enqueue(object : Callback<TracksResponse> {
                                override fun onResponse(
                                    call: Call<TracksResponse>,
                                    response: Response<TracksResponse>) {
                                    if (response.code() == 200) {
                                        tracks.clear()
                                        if (response.body()?.results?.isNotEmpty() == true) {
                                            onlyTracksListVisible()
                                            tracks.clear()
                                            tracks.addAll(response.body()?.results!!)
                                            adapter.notifyDataSetChanged()
                                        }
                                        if (tracks.isEmpty()) {
                                            onlyLinearLayoutNothingFoundVisible()
                                        }
                                    } else {
                                        onlyLinearLayoutSomethingWentWrongVisible()
                                    }
                                }
                                override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
                                    onlyLinearLayoutSomethingWentWrongVisible()
                                }
                            })
                        }
            }
        }
        inputEditText.addTextChangedListener(textWatcher)
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private var valueTextInput: String = DEFAULT_TEXT_INPUT

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TEXT_INPUT, valueTextInput)
    }
    companion object {
        const val TEXT_INPUT = "TEXT_INPUT"
        const val DEFAULT_TEXT_INPUT = ""
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        valueTextInput = savedInstanceState.getString(TEXT_INPUT, DEFAULT_TEXT_INPUT)
    }
    private fun allViewsVisibleGone() {
        tracksList.visibility = View.GONE
        linearLayoutSomethingWentWrong.visibility = View.GONE
        linearLayoutNothingFound.visibility = View.GONE
    }
    private fun onlyTracksListVisible() {
        tracksList.visibility = View.VISIBLE
        linearLayoutSomethingWentWrong.visibility = View.GONE
        linearLayoutNothingFound.visibility = View.GONE
    }
    private fun onlyLinearLayoutNothingFoundVisible() {
        tracksList.visibility = View.GONE
        linearLayoutSomethingWentWrong.visibility = View.GONE
        linearLayoutNothingFound.visibility = View.VISIBLE
    }
    private fun onlyLinearLayoutSomethingWentWrongVisible() {
        tracksList.visibility = View.GONE
        linearLayoutSomethingWentWrong.visibility = View.VISIBLE
        linearLayoutNothingFound.visibility = View.GONE
    }
}