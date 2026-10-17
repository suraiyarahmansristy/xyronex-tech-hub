package com.example.xyronextechhub

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_screen)

        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        var isPasswordVisible = false

        etPassword.setOnTouchListener { _, event ->
            val DRAWABLE_END = 2
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = etPassword.compoundDrawablesRelative[DRAWABLE_END]
                    ?: etPassword.compoundDrawables[DRAWABLE_END]

                if (drawableEnd != null && event.rawX >= (etPassword.right - drawableEnd.bounds.width() - etPassword.paddingEnd)) {
                    isPasswordVisible = !isPasswordVisible
                    if (isPasswordVisible) {
                        etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                        etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            android.R.drawable.ic_lock_lock, 0, R.drawable.baseline_visibility_24, 0
                        )
                    } else {
                        etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                        etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            android.R.drawable.ic_lock_lock, 0, R.drawable.baseline_visibility_off_24, 0
                        )
                    }
                    etPassword.setSelection(etPassword.text.length)
                    return@setOnTouchListener true
                }
            }
            false
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}