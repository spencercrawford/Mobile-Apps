package com.example.finalproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AlertDialog
import android.text.format.DateFormat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE
import java.util.*

class RegisterActivity : AppCompatActivity() {

    lateinit var username: EditText
    lateinit var random: String
    lateinit var email: EditText
    lateinit var password: EditText
    var firebaseAuth = FirebaseAuth.getInstance()
    lateinit var user: FirebaseUser
    lateinit var generate: Button
    lateinit var registerButton: Button
    lateinit var alreadyMemberButton: Button
    lateinit var toolbar: android.support.v7.widget.Toolbar
    lateinit var sharedPreferences: SharedPreferences
    val PREF_FILENAME = "com.example.finalproject"
    val firebaseDatabase = FirebaseDatabase.getInstance()
    var dbReference = firebaseDatabase.reference
    var randomClicked: Boolean = FALSE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        toolbar = this.findViewById(R.id.registerToolbar)
        setSupportActionBar(toolbar)

        if (firebaseAuth.currentUser != null) {
            firebaseAuth.signOut()
            AuthUI.getInstance().signOut(applicationContext)
        }

        username = findViewById(R.id.registerUserName)
        email = findViewById(R.id.registerEmail)
        password = findViewById(R.id.registerPassword)
        generate = findViewById(R.id.generateRandomUserNameButton)
        registerButton = findViewById(R.id.registerButton)
        alreadyMemberButton = findViewById(R.id.alreadyMemberButton)

        sharedPreferences = getSharedPreferences(PREF_FILENAME, 0)

        registerButton.setOnClickListener {

            var username: String = username.text.toString()
            var email: String = email.text.toString()
            var password: String = password.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Please Enter Your Email.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Please Enter A Password.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(username)) {
                Toast.makeText(applicationContext, "Please Enter A User Name.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (username.length < 6) {
                Toast.makeText(applicationContext, "User Name Must Be Longer Than 6 Characters.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (username.length > 24) {
                Toast.makeText(applicationContext, "User Name Must Be Less Than 12 Characters.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(applicationContext, "Password Must Be Longer Than 6 Characters.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (sharedPreferences.contains(username)) {
                Toast.makeText(applicationContext, "User Name Already Exists!.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            user = firebaseAuth.currentUser!!
                            var profileUpdates: UserProfileChangeRequest = if (randomClicked) {
                                UserProfileChangeRequest.Builder()
                                    .setDisplayName(random)
                                    .build()
                            } else {
                                UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build()
                            }

                            user.updateProfile(profileUpdates).addOnCompleteListener {
                                if(it.isSuccessful) {
                                    Log.i("OUTPUT", "Profile Updated!")
                                }
                                if(it.isCanceled) {
                                    Log.i("OUTPUT", "Profile Not Updated!")
                                }
                            }
                            val time = Date().time
                            val startDate = DateFormat.format("MM-dd-yyyy", time).toString()

                            val newUser = ChatUser(username, startDate)
                            dbReference.child("users").push().setValue(newUser)
                            Toast.makeText(applicationContext, "Account Created!", Toast.LENGTH_SHORT).show()
                            FirebaseAuth.getInstance().signOut()
                            AuthUI.getInstance().signOut(applicationContext)
                            startActivity(Intent(applicationContext, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "Failure to Register!", Toast.LENGTH_SHORT).show()
                        }
                }
        }

        alreadyMemberButton.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

        generate.setOnClickListener {
            randomClicked = TRUE
            random = getRandomName()
            var editor = sharedPreferences.edit()
            editor.putString("random", random)
            editor.apply()
            username.text.clear()
            username.text.insert(0, random)
            Log.d("OUTPUT", "random name: $random")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_register, menu)
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

    fun getRandomName(): String {
        val adjs = arrayOf("autumn", "hidden", "bitter", "misty", "silent", "empty", "dry", "dark", "summer", "icy", "delicate", "quiet", "white", "cool", "spring", "winter", "patient", "twilight", "dawn", "crimson", "wispy", "weathered", "blue", "billowing", "broken", "cold", "damp", "falling", "frosty", "green", "long", "late", "lingering", "bold", "little", "morning", "muddy", "old", "red", "rough", "still", "small", "sparkling", "throbbing", "shy", "wandering", "withered", "wild", "black", "young", "holy", "solitary", "fragrant", "aged", "snowy", "proud", "floral", "restless", "divine", "polished", "ancient", "purple", "lively", "nameless")
        val nouns = arrayOf("waterfall", "river", "breeze", "moon", "rain", "wind", "sea", "morning", "snow", "lake", "sunset", "pine", "shadow", "leaf", "dawn", "glitter", "forest", "hill", "cloud", "meadow", "sun", "glade", "bird", "brook", "butterfly", "bush", "dew", "dust", "field", "fire", "flower", "firefly", "feather", "grass", "haze", "mountain", "night", "pond", "darkness", "snowflake", "silence", "sound", "sky", "shape", "surf", "thunder", "violet", "water", "wildflower", "wave", "water", "resonance", "sun", "wood", "dream", "cherry", "tree", "fog", "frost", "voice", "paper", "frog", "smoke", "star")
        return ((adjs[Math.floor(Math.random() * adjs.size).toInt()] +
                "_" +
                nouns[Math.floor(Math.random() * nouns.size).toInt()]))
    }
}