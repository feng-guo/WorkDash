package com.example.workdash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonSignUp = findViewById(R.id.buttonSignUp)

        buttonSignUp.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        val firstName = editTextFirstName.text.toString()
        val lastName = editTextLastName.text.toString()
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()

        if (firstName.isEmpty() || lastName.isEmpty()) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
        } else if (!isEmailValid(email)) {
            Toast.makeText(this, "Please input a valid email", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Please input a strong password", Toast.LENGTH_SHORT).show()
        }
        else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user: FirebaseUser? = auth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(firstName)
                            .build()
                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { profileUpdateTask ->
                                if (profileUpdateTask.isSuccessful) {
                                    // Sign-up and profile update successful, proceed with further actions
                                    Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show()
                                    // Example: Navigate to the home activity
                                    // val intent = Intent(this, HomeActivity::class.java)
                                    // startActivity(intent)
                                    // finish() // Optional: Close the current activity
                                } else {
                                    val exception = task.exception
                                    Toast.makeText(this, "Sign-up failed: ${exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        // Sign-up failed, display an error message to the user
                        Toast.makeText(this, "Sign-up Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val pattern = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex()
        return email.matches(pattern)
    }
}
