package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class NewPasswordActivity : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var newPasswordButton: Button
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var toolbar: android.support.v7.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)

        toolbar = findViewById(R.id.forgotPasswordToolbar)
        setSupportActionBar(toolbar)

        email = findViewById(R.id.newPasswordEmail)
        newPasswordButton = findViewById(R.id.submitPasswordButton)
        firebaseAuth = FirebaseAuth.getInstance()

        newPasswordButton.setOnClickListener {

            var email = email.text.toString()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Please Enter Your Email", Toast.LENGTH_SHORT).show()
            }

            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Password Reset Link Sent!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, LoginActivity::class.java))

                } else {
                    Toast.makeText(applicationContext, "Error Sending Password Reset Link!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_forgot_password, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_back -> {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            true
        } R.id.action_exit -> {
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
}