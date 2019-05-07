package com.example.finalproject

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList
import kotlin.text.Typography.quote

class MainActivity : AppCompatActivity() {

    var TAG = "OUTPUT"

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var toolbar: android.support.v7.widget.Toolbar
    lateinit var sharedPreferences: SharedPreferences
    val PREF_FILENAME = "com.example.finalproject"
    var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    lateinit var dbReference: DatabaseReference
    lateinit var currentChatUser: ChatUser
    var listChatUser = ArrayList<ChatUser>()
    lateinit var quoteTextView: TextView
    lateinit var authorTextView: TextView
    lateinit var mainQuoter: Quoter
    lateinit var quote: Quoter.Quote

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(toolbar)

        sharedPreferences = getSharedPreferences(PREF_FILENAME, 0)

        sharedPreferences.edit().putInt("chat", 1).apply()

        var startDate: TextView = findViewById(R.id.memberSincetTextView)
        var hello: TextView = findViewById(R.id.greetingTextBox)

        dbReference = firebaseDatabase.reference

        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
               val children = p0.children
                children.forEachIndexed { index, element ->
                    listChatUser.add(index, element.getValue(ChatUser::class.java)!!)
                    //Log.i(TAG, element.value.toString())
                }
                for (chatUser in listChatUser) {
                    if (chatUser.getName() == firebaseAuth.currentUser!!.displayName) {
                        currentChatUser = chatUser
                        hello.text = "Welcome ${currentChatUser.getName()}"
                        startDate.text = "Member Since: ${currentChatUser.getStartDate()}"
                    }
                }
            }
        }
        dbReference.child("users").addListenerForSingleValueEvent(listener)

        quoteTextView = findViewById(R.id.quoteTextView)
        authorTextView = findViewById(R.id.authorTextView)
        mainQuoter = Quoter(resources.openRawResource(R.raw.quotes))
        getNewQuote()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_logout -> {
            FirebaseAuth.getInstance().signOut()
            AuthUI.getInstance().signOut(applicationContext)
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            true
        }
        R.id.action_chat -> {
            sharedPreferences.edit().putInt("chat", 0).apply()
            startActivity(Intent(applicationContext, ChatActivity::class.java))
            true
        }
        R.id.action_delete -> {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Delete Account?")
            alertDialogBuilder.setMessage("Deletions are permanent").setCancelable(false).setPositiveButton("Yes") { _, id ->
                val listener = object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}
                    override fun onDataChange(p0: DataSnapshot) {
                        val children = p0.children
                        children.forEachIndexed { index, element ->
                            if (element.value.toString().contains(firebaseAuth.currentUser!!.displayName.toString())) {
                                dbReference.child("users").child(element.key.toString()).removeValue()
                            }
                        }
                    }
                }
                dbReference.child("users").addListenerForSingleValueEvent(listener)
                firebaseAuth.currentUser!!.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(applicationContext, "User deleted!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(applicationContext, RegisterActivity::class.java))
                            finish()
                        }
                    }
            }.setNegativeButton("No") { dialog, _ -> dialog.cancel() }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
            true
        }
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

    fun getNewQuote() {
        quote = mainQuoter.randomQuote
        quoteTextView.text = quote.text
        authorTextView.text = quote.author
    }


    override fun onStart() {
        super.onStart()
        Log.i(TAG, "Main onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "Main onStop")
        if (sharedPreferences.getInt("chat", 1) == 1) {
            FirebaseAuth.getInstance().signOut()
            AuthUI.getInstance().signOut(applicationContext)
        }
    }
}















