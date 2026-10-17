package com.example.xyronextechhub

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotPasswordTv: TextView
    private lateinit var googleLoginBtn: Button
    private lateinit var facebookLoginBtn: Button
    private lateinit var signUpTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        // UI Components Initialize
        emailEditText = findViewById(R.id.etEmail)
        passwordEditText = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnLogin)
        forgotPasswordTv = findViewById(R.id.tvForgotPassword)
        googleLoginBtn = findViewById(R.id.btnGoogle)
        facebookLoginBtn = findViewById(R.id.btnfacebook)
        signUpTv = findViewById(R.id.tvSignUp)

        // Login Button Click Listener
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            }
        }

        // Forgot Password Click Listener
        forgotPasswordTv.setOnClickListener {
            Toast.makeText(this, "Forgot Password Clicked", Toast.LENGTH_SHORT).show()
        }

        // Continue with Google Click Listener
        googleLoginBtn.setOnClickListener {
            Toast.makeText(this, "Google Sign-In Clicked", Toast.LENGTH_SHORT).show()
        }

        // Continue with Facebook Click Listener
        facebookLoginBtn.setOnClickListener {
            Toast.makeText(this, "Facebook Sign-In Clicked", Toast.LENGTH_SHORT).show()
        }

        // Sign Up Click Listener
        signUpTv.setOnClickListener {
            Toast.makeText(this, "Sign Up Clicked", Toast.LENGTH_SHORT).show()
        }
    }
}