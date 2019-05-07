package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.format.DateFormat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.firebase.ui.database.FirebaseListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ChatActivity: AppCompatActivity() {

    lateinit var adapter: FirebaseListAdapter<ChatMessage>

    val firebaseDatabase = FirebaseDatabase.getInstance()
    var dbReference = firebaseDatabase.reference
    val firebaseAuth = FirebaseAuth.getInstance()
    lateinit var toolbar: Toolbar
    lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_main)

        toolbar = findViewById(R.id.chatToolbar)
        setSupportActionBar(toolbar)

        if (firebaseAuth.currentUser == null) {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
        else {
            user = firebaseAuth.currentUser!!
            Toast.makeText(this,
                ("Welcome ${user.displayName}"), Toast.LENGTH_SHORT).show()
        }

        val fab:FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener{

            val input: EditText = findViewById(R.id.input)
            val text: String = input.text.toString()
            Log.i("OUTPUT", "TEXT: $text USER: ${user.displayName}")
            val chatMessage = ChatMessage(text, user.displayName!!)
            dbReference.push().setValue(chatMessage).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i("OUTPUT", "SUCCESS")
                }
            }
            input.setText("")
        }
        displayChatMessages()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_chat, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_back -> {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            true
        }
        R.id.action_logout -> {
            firebaseAuth.signOut()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
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

    private fun displayChatMessages() {

        val listOfMessages: ListView = findViewById(R.id.list_of_messages)

        adapter = object: FirebaseListAdapter<ChatMessage>(this, ChatMessage::class.java,
            R.layout.activity_message, dbReference) {
            override fun populateView(v: View, model: ChatMessage, position: Int) {
                val messageTime  = v.findViewById(R.id.message_time) as TextView
                val messageUser = v.findViewById(R.id.message_user) as TextView
                val messageText = v.findViewById(R.id.message_text) as TextView

                messageTime.text = DateFormat.format("MM-dd-yyyy (HH:mm)",
                    model.getMessageTime())
                messageUser.text = model.getMessageUser()
                messageText.text = model.getMessageText()
            }
        }
        listOfMessages.adapter = adapter
    }
}