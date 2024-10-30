package com.example.playlistmaker
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val buttonBack = findViewById<ImageView>(R.id.arrow_back_light)

        buttonBack.setOnClickListener {
            val homeDisplayIntent = Intent(this, MainActivity::class.java)
            startActivity(homeDisplayIntent)
        }

    }
}