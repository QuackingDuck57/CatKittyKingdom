package com.example.catkittykingdom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.catkittykingdom.databinding.ActivityTutorialBinding

class TutorialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTutorialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonTutorialBack.setOnClickListener {
            finish()
        }
    }

}