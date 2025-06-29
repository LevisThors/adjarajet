package com.example.adjarajet

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val buttonSignIn = findViewById<Button>(R.id.buttonSignIn)

        buttonSignIn.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            if (email.isNotEmpty()) {
                sharedPreferences.edit().putString("USER_EMAIL", email).apply()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        checkIfLoggedIn()
    }

    private fun checkIfLoggedIn() {
        val email = sharedPreferences.getString("USER_EMAIL", null)
        if (email != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
