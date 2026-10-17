package com.example.xyronextechhub

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)

        // ফায়ারবেস অথেন্টিকেশন ইনিশিয়ালাইজ করা
        auth = FirebaseAuth.getInstance()

        // লেআউট থেকে আইডিগুলো কানেক্ট করা
        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etSignupEmail = findViewById<EditText>(R.id.etSignupEmail)
        val etSignupPassword = findViewById<EditText>(R.id.etSignupPassword)
        val etInstitute = findViewById<EditText>(R.id.etInstitute)
        val etMajor = findViewById<EditText>(R.id.etMajor)
        val cbTerms = findViewById<CheckBox>(R.id.cbTerms)
        val btnCreateAccount = findViewById<Button>(R.id.btnCreateAccount)
        val tvLoginLink = findViewById<TextView>(R.id.tvLoginLink)

        // Create Account বাটনে ক্লিক করলে যা হবে
        btnCreateAccount.setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val email = etSignupEmail.text.toString().trim()
            val password = etSignupPassword.text.toString().trim()
            val institute = etInstitute.text.toString().trim()
            val major = etMajor.text.toString().trim()

            // ফিল্ডগুলো খালি আছে কি না চেক করা
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || institute.isEmpty() || major.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // পাসওয়ার্ড কমপক্ষে ৬ অক্ষরের হতে হবে
            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // টার্মস অ্যান্ড কন্ডিশন চেক করা হয়েছে কি না
            if (!cbTerms.isChecked) {
                Toast.makeText(this, "You must agree to the terms and conditions", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ফায়ারবেসে ইউজার অ্যাকাউন্ট তৈরি করা
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_SHORT).show()

                        // সফলভাবে অ্যাকাউন্ট তৈরি হলে ড্যাশবোর্ডে (MainActivity) চলে যাবে
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

        // লগইন লিংকে ক্লিক করলে লগইন পেজে ফিরে যাওয়ার জন্য
        tvLoginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}