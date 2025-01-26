package com.mawuli.simplecallapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mawuli.simplecallapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLogin()
    }

    private fun setupLogin() {
        binding.loginButton.setOnClickListener {
            val name = binding.userName.text.toString().trim()
            val id = binding.userId.text.toString().trim()

            if (name.isNotEmpty() && id.isNotEmpty()) {
                // Navigate to main call screen
                val intent = Intent(this, CallActivity::class.java).apply {
                    putExtra(USERNAME, name)
                    putExtra(USER_ID, id)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter both name and user ID", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


}