package com.example.catkittykingdom

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.catkittykingdom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainStartButton.setOnClickListener {
            val gameIntent = Intent(this@MainActivity, GameActivity::class.java)
            startActivity(gameIntent)
        }
        binding.mainHowtoplayButton.setOnClickListener {
            val tutIntent = Intent(this@MainActivity, TutorialActivity::class.java)
            startActivity(tutIntent)
        }
        binding.mainSettingsButton.setOnClickListener {
            val setIntent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(setIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        val highScorePrefs =  getSharedPreferences(GameActivity.HIGH_SCORE_PREFS, Context.MODE_PRIVATE)  ?: return
        val highScore = highScorePrefs.getInt(GameActivity.PREFS_MONTHS, 0)
        Log.d("MainActivity", "determineLose: $highScore")

        binding.mainTextviewScore.text = "Highscore: $highScore"
    }
}