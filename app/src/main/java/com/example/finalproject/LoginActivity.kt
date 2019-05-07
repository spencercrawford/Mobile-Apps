package com.example.finalproject

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.text.format.DateFormat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    var TAG = "OUTPUT"

    lateinit var loginEmail: EditText
    lateinit var loginPassword: EditText
    lateinit var loginButton: Button
    lateinit var registerButton: Button
    lateinit var newPasswordButton: Button

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var toolbar: android.support.v7.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        toolbar = findViewById(R.id.loginToolbar)
        setSupportActionBar(toolbar)

        loginEmail = findViewById(R.id.loginEmail)
        loginPassword = findViewById(R.id.loginPassword)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        newPasswordButton = findViewById(R.id.forgotPasswordButton)

        firebaseAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {

            var email: String = loginEmail.text.toString()
            var password: String = loginPassword.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Please Enter Your Email.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Please Enter Your Password.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
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