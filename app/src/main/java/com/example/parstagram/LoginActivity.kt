package com.example.parstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        /*val firstObject = ParseObject("FirstClass")
        firstObject.put("message","Hey ! First message from android. Parse is now connected")
        firstObject.saveInBackground {
            if (it != null){
                it.localizedMessage?.let { message -> Log.e("MainActivity", message) }
            }else{
                Log.d("MainActivity","Object saved.")
            }
        }*/

        //check if user is logged in
        if(ParseUser.getCurrentUser() != null){
            goToMainActivity()
        }

        findViewById<Button>(R.id.Login_button).setOnClickListener {
            val username = findViewById<TextView>(R.id.et_username).text.toString()
            val password = findViewById<TextView>(R.id.et_password).text.toString()
            loginUser(username, password)
        }

        findViewById<Button>(R.id.signupBtn).setOnClickListener {
            val username = findViewById<TextView>(R.id.et_username).text.toString()
            val password = findViewById<TextView>(R.id.et_password).text.toString()
            signUpUser(username, password)
        }


    }

    private fun signUpUser(username: String, password: String){
        // Create the ParseUser
        val user = ParseUser()

        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                Log.i(TAG, "Sign up successful")
                goToMainActivity()
                Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Sign up unsuccessful", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                Log.i(TAG, "Successfull login")
                goToMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Error loggin in", Toast.LENGTH_SHORT).show()
            }})
        )
    }

    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object{
        val TAG = "LoginActivity"
    }
}