package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch

const val PREFERENCE = "preference"
const val SWITCH_KEY = "switch_key"

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)



        val buttonBack = findViewById<ImageView>(R.id.arrow_back_light)
        buttonBack.setOnClickListener {
            Intent(this, MainActivity::class.java)
            finish()
        }

        val shareApp = findViewById<ImageView>(R.id.shareApp)
        shareApp.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.link_to_the_course))
            shareIntent.type = "text/plain"
            startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))
        }

        val writeToSupport = findViewById<ImageView>(R.id.writeToSupport)
        writeToSupport.setOnClickListener {
            val subjectText = getString(R.string.subject_text)
            val text = getString(R.string.message_text)
            val writeToSupportIntent = Intent(Intent.ACTION_SEND)
            writeToSupportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_adress)))
            writeToSupportIntent.putExtra(Intent.EXTRA_SUBJECT, subjectText)
            writeToSupportIntent.putExtra(Intent.EXTRA_TEXT, text)
            writeToSupportIntent.type = "message/rfc822"
            startActivity(writeToSupportIntent)
        }

        val userAgreement = findViewById<ImageView>(R.id.userAgreement)
        userAgreement.setOnClickListener {
            val url = getString(R.string.offer)
            val userAgreementIntent = Intent(Intent.ACTION_VIEW)
            userAgreementIntent.data = Uri.parse(url)
            startActivity(userAgreementIntent)
        }
        val themeSwitcher = findViewById<Switch>(R.id.themeSwitcher)

        val sharedPrefs = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
        themeSwitcher.setOnCheckedChangeListener { _, isChecked ->
            (applicationContext as App).switchTheme(isChecked)
            sharedPrefs.edit()
                .putBoolean(SWITCH_KEY, isChecked)
                .apply()
        }
        themeSwitcher.isChecked = sharedPrefs.getBoolean(SWITCH_KEY, false)
    }
}