package com.example.xyronextechhub

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.TextViewCompat
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
        val tvSignUp = findViewById<TextView>(R.id.tvSignUp) // এখানে tvSignUp করা হয়েছে

        // চোখের আইকনটির কালার হালকা সাদা/নীলচে করে দেওয়া যেন ডার্ক ব্যাকগ্রাউন্ডে স্পষ্ট দেখা যায়
        TextViewCompat.setCompoundDrawableTintList(
            etPassword,
            ColorStateList.valueOf(Color.parseColor("#A5B6CE"))
        )

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
                            android.R.drawable.ic_lock_lock, 0, R.drawable.outline_visibility_24, 0
                        )
                    } else {
                        etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                        etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            android.R.drawable.ic_lock_lock, 0, R.drawable.outline_visibility_off_24, 0 // এখানে আপনার ড্রয়েবল ফোল্ডারের নাম অনুযায়ী দেওয়া হলো
                        )
                    }
                    // আইকন পরিবর্তনের পরও যেন কালার ঠিক থাকে
                    TextViewCompat.setCompoundDrawableTintList(
                        etPassword,
                        ColorStateList.valueOf(Color.parseColor("#A5B6CE"))
                    )
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
                if (password.length < 6) {
                    Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { signUpTask ->
                                    if (signUpTask.isSuccessful) {
                                        Toast.makeText(this, "Account Created & Logged In!", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this, MainActivity::class.java))
                                        finish()
                                    } else {
                                        Toast.makeText(this, "Error: ${signUpTask.exception?.message}", Toast.LENGTH_LONG).show()
                                    }
                                }
                        }
                    }
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        // সাইন আপ পেজে যাওয়ার লজিক
        tvSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}