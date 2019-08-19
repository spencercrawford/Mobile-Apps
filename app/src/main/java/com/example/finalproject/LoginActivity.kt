package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    var TAG = "NATURAL"

    lateinit var loginEmail: EditText
    lateinit var loginPassword: EditText
    lateinit var loginButton: Button
    lateinit var registerButton: Button
    lateinit var newPasswordButton: Button

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        toolbar = findViewById(R.id.loginToolbar)
        setSupportActionBar(toolbar)

        loginEmail = findViewById(R.id.loginEmail)
        loginPassword = findViewById(R.id.loginPhoneNumber)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        newPasswordButton = findViewById(R.id.newPasswordButton)

        firebaseAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {

            var username: String = loginEmail.text.toString()
            var password: String = loginPassword.text.toString()

            if (TextUtils.isEmpty(username)) {
                Toast.makeText(applicationContext, "Please Enter Your User Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Authorization Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        registerButton.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        newPasswordButton.setOnClickListener {
            startActivity(Intent(applicationContext, NewPasswordActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_login, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_exit -> {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Exit Application?")
            alertDialogBuilder.setMessage("Click yes to exit").setCancelable(false).setPositiveButton("Yes") { _, id ->
                moveTaskToBack(true)
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(1)
            }.setNegativeButton("No") { dialog, _ -> dialog.cancel() }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "Login onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "Login onStop")
    }
}