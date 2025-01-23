package com.example.playlistmaker



import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale


class PlayerActivity : AppCompatActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val buttonBack = findViewById<ImageView>(R.id.arrowBack)
        buttonBack.setOnClickListener {
            finish()
        }

        val track = intent.getParcelableExtra<Track>("Track")



        val nameOfSong  = findViewById<TextView>(R.id.songName)
        nameOfSong.text = track?.trackName
        val authorOfSong  = findViewById<TextView>(R.id.authorOfSong)
        authorOfSong.text = track?.artistName
        val durationOfSong  = findViewById<TextView>(R.id.allDurationSong)
        durationOfSong.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track?.trackTimeMillis)
        val collectionOfSong  = findViewById<TextView>(R.id.albumSong)
        collectionOfSong.text = track?.collectionName
        val releaseDateOfSong  = findViewById<TextView>(R.id.yearOfSong)
        releaseDateOfSong.text = track?.releaseDate
        val genreOfSong  = findViewById<TextView>(R.id.genreOfSong)
        genreOfSong.text = track?.primaryGenreName
        val countryOfSong  = findViewById<TextView>(R.id.countryOfSong)
        countryOfSong.text = track?.country
        val albumCover = findViewById<ImageView>(R.id.bigAlbumCover)
        val imageUrl = track?.artworkUrl100?.replaceAfterLast('/',"512x512bb.jpg")

        Glide.with(applicationContext)
            .load(imageUrl)
            .placeholder(R.drawable.big_placeholder)
            .centerCrop()
            .transform(RoundedCorners(8))
            .into(albumCover)


    }
}