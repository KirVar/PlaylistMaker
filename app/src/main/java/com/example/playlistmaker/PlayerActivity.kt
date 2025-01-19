package com.example.playlistmaker


import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Locale


class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val buttonBack = findViewById<ImageView>(R.id.arrowBack)
        buttonBack.setOnClickListener {
            Intent(this, MainActivity::class.java)
            finish()
        }

        val nameOfSong  = findViewById<TextView>(R.id.songName)
        nameOfSong.text = intent.getStringExtra("keyNameSong")

        val authorOfSong  = findViewById<TextView>(R.id.authorOfSong)
        authorOfSong.text = intent.getStringExtra("keyAuthorSong")

        val durationOfSong  = findViewById<TextView>(R.id.allDurationSong)
        durationOfSong.text = intent.getStringExtra("keyDurationSong")

        val collectionOfSong  = findViewById<TextView>(R.id.albumSong)
        collectionOfSong.text = intent.getStringExtra("keyCollectionSong")

        val releaseDateOfSong  = findViewById<TextView>(R.id.yearOfSong)
        releaseDateOfSong.text = intent.getStringExtra("keyDataSong")

        val genreOfSong  = findViewById<TextView>(R.id.genreOfSong)
        genreOfSong.text = intent.getStringExtra("keyGenreSong")

        val countryOfSong  = findViewById<TextView>(R.id.countryOfSong)
        countryOfSong.text = intent.getStringExtra("keyCountrySong")
    }
}