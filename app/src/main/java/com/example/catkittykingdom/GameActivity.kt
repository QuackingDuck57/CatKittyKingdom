package com.example.catkittykingdom

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.catkittykingdom.databinding.ActivityGameBinding


class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    var months = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.GameTextViewScore.text = "Months in power: $months"

        binding.activityGamePassTime.setOnClickListener {
            binding.activityGamePassTime.isEnabled = false
            binding.activityGamePassTime.isClickable = false
            months += 1
            binding.GameTextViewScore.text = "Months in power: $months"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.game_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.game_menu_stats -> {
                val statsIntent = Intent(this@GameActivity, StatsActivity::class.java) //MENU INFLATER NOT WORKING
                startActivity(statsIntent)
            }
            R.id.game_menu_settings ->
            {
                val setIntent = Intent(this@GameActivity, SettingsActivity::class.java)
                startActivity(setIntent)
            }
            R.id.game_menu_quit -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}