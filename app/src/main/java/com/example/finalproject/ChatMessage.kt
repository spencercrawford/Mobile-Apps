package com.example.finalproject

import java.util.*

class ChatMessage {

    private var messageTime: Long = 0L
    private var messageUser: String = ""
    private var messageText: String = ""

    constructor()

    constructor(messageText: String, messageUser: String) {
        this.messageTime = Date().time
        this.messageUser = messageUser
        this.messageText = messageText
    }

    fun getMessageTime():Long {
        return messageTime
    }

    fun setMessageTime(messageTime:Long) {
        this.messageTime = messageTime
    }

    fun getMessageUser():String {
        return messageUser
    }

    fun setMessageUser(messageUser:String) {
        this.messageUser = messageUser
    }

    fun getMessageText():String {
        return messageText
    }
    fun setMessageText(messageText:String) {
        this.messageText = messageText
    }
}