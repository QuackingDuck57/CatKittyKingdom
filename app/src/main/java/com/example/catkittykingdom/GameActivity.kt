package com.example.catkittykingdom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.catkittykingdom.databinding.ActivityGameBinding


class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    var months = 0
    var rating = 50
    var pawsos = 100
    var numMonthsUnfavored = 0

    companion object {
        val PREFS_MONTHS = "months"
        val HIGH_SCORE_PREFS = "highscoreprefs"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // load scenarios from json into a list

        //setting default values
        binding.gameImageview.setBackgroundResource(R.drawable.king_cat2)
        binding.GameTextViewScore.text = "\uD83D\uDC51 Months in power: $months"
        binding.GameTextviewProgress.text = "\uD83D\uDCC8 Approval rating: $rating%"
        binding.gameTextViewStrikes.text = "\uD83D\uDE0A Strikes: $numMonthsUnfavored/3"
        binding.gameTextViewMoney.text = "\uD83E\uDD11 Pawsos: $pawsos"
        //setting up time passing ability
        binding.activityGamePassTime.setOnClickListener {
            binding.activityGamePassTime.isEnabled = false
            binding.activityGamePassTime.isClickable = false
            months += 1
            binding.GameTextViewScore.text = "\uD83D\uDC51 Months in power: $months"

            // pull random scenario
            loadQuestions()

            //popup window
            val inflater = layoutInflater
            val dialoglayout: View = inflater.inflate(R.layout.item_popup, null)
            val text = dialoglayout.findViewById<TextView>(R.id.item_textView_popup)
            val button1 = dialoglayout.findViewById<Button>(R.id.item_button_op1)
            val button2 = dialoglayout.findViewById<Button>(R.id.item_button_op2)
            val button3 = dialoglayout.findViewById<Button>(R.id.item_button_op3)
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)

            builder.setView(dialoglayout)
            builder.setCancelable(false)
            var alertDialog = builder.show()
            button1.setOnClickListener {
                setProgressRating(10)
                setMoneyAmount(-50)
                Toast.makeText(this, "AR: $rating% Pawsos: $pawsos", Toast.LENGTH_SHORT).show()
                binding.activityGamePassTime.isEnabled = true
                binding.activityGamePassTime.isClickable = true
                alertDialog.dismiss()
                determineStrike()
                determineImages()
            }
            button2.setOnClickListener {
                setProgressRating(-20)
                setMoneyAmount(-10)
                Toast.makeText(this, "AR: $rating% Pawsos: $pawsos", Toast.LENGTH_SHORT).show()
                binding.activityGamePassTime.isEnabled = true
                binding.activityGamePassTime.isClickable = true
                alertDialog.dismiss()
                determineStrike()
                determineImages()
            }
            button3.setOnClickListener {
                setProgressRating(20)
                setMoneyAmount(100)
                Toast.makeText(this, "AR: $rating% Pawsos: $pawsos", Toast.LENGTH_SHORT).show()
                binding.activityGamePassTime.isEnabled = true
                binding.activityGamePassTime.isClickable = true
                alertDialog.dismiss()
                determineStrike()
                determineImages()
            }
        }


    }

    private fun loadQuestions() {
        //load questions from JSON
        val inputStream = resources.openRawResource(R.raw.stuff)
        val jsonString = inputStream.bufferedReader().use {
            it.readText()
        }
        Log.d(TAG, "onCreate: jsonString $jsonString")
        //next steps:
        //make your Question data class
        //use this tutorial
        //https://medium.com/@hissain.khan/parsing-with-google-gson-library-in-android-kotlin
        //scroll down to "parsing between a collection, list, or array"
        val gson = Gson()
        val qType = object : TypeToken<List<Question>>() {}.type
        val questions = gson.fromJson<List<Question>>(jsonString, qType)
        Log.d(TAG, "loadQuestions: $questions")
        stuff = Stuff(questions)
        textMain.text = quiz.cQuestion
        button1.text = quiz.cChoices.get(quiz.q)
        button2.text = quiz.cChoices.get(quiz.q)
        button3.text = quiz.cChoices.get(quiz.q)
    }

    fun determineStrike() {
        if (rating <= 40 || pawsos < 0) {
            numMonthsUnfavored += 1
            if (numMonthsUnfavored == 1) {
                binding.gameTextViewStrikes.text = "\uD83D\uDE20 Strikes: $numMonthsUnfavored/3"
            } else if (numMonthsUnfavored == 2) {
                binding.gameTextViewStrikes.text = "\uD83D\uDE21 Strikes: $numMonthsUnfavored/3"
            } else if (numMonthsUnfavored == 3) {
                binding.gameTextViewStrikes.text = "\uD83E\uDD2C Strikes: $numMonthsUnfavored/3"
            }
            determineLose()
        }
    }

    fun determineImages() {
        if (pawsos >= 100 && rating > 40) {
            binding.gameImageview.setBackgroundResource(R.drawable.king_cat2)
        }
        if (pawsos < 100 && rating > 40) {
            binding.gameImageview.setBackgroundResource(R.drawable.broke_cat)
        }
        if (pawsos >= 1000 && rating > 40) {
            binding.gameImageview.setBackgroundResource(R.drawable.rich_king_cat)
        }
        if (pawsos < 1000 && rating == 100) {
            binding.gameImageview.setBackgroundResource(R.drawable.king_cat_ultimate)
        }
        if (pawsos >= 1000 && rating == 100) {
            binding.gameImageview.setBackgroundResource(R.drawable.cat_napoleon)
        }
        if (rating <= 40) {
            binding.gameImageview.setBackgroundResource(R.drawable.sad_cat)
        }
        if (pawsos < 0) {
            binding.gameImageview.setBackgroundResource(R.drawable.sad_cat)
        }
    }

    fun determineLose() {
        if (numMonthsUnfavored == 3) {
            val highScorePrefs = getSharedPreferences(HIGH_SCORE_PREFS, Context.MODE_PRIVATE) ?: return
            var highScore = highScorePrefs.getInt(PREFS_MONTHS, 0)
            Log.d("GameActivity", "determineLose: months:$months highscore:$highScore")

            if (months > highScore) {
                with (highScorePrefs.edit()) {
                    putInt(PREFS_MONTHS, months)
                    apply()
                }
            }
            lost() //Game over
        }
    }

    fun setMoneyAmount(num: Int) {
        pawsos += num
        binding.gameTextViewMoney.text = "\uD83E\uDD11 Pawsos: $pawsos"
    }
    fun setProgressRating(num: Int) {
        rating += num
        binding.GameProgressbar.setProgress(rating)
        if (rating > 100) {
            rating = 100
        }
        binding.GameTextviewProgress.text = "\uD83D\uDCC8 Approval rating: $rating%"
    }

    fun lost() {
        val inflater = layoutInflater
        val lostlayout: View = inflater.inflate(R.layout.item_lost, null)
        val score = lostlayout.findViewById<TextView>(R.id.item_textView_score)
        val button = lostlayout.findViewById<Button>(R.id.item_button_lost)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)


        builder.setView(lostlayout)
        builder.setCancelable(false)
        builder.show()

        score.text = "Score: $months"

        button.setOnClickListener {
            finish()
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
                val statsIntent = Intent(this@GameActivity, StatsActivity::class.java)
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