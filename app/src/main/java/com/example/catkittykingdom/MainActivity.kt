package com.example.catkittykingdom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
}