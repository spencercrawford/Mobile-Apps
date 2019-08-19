package com.example.finalproject

import java.util.*

class ChatMessage {

    private var messageUser: String = ""
    private var messageText: String = ""

    constructor()

    constructor(messageText: String, messageUser: String) {
        this.messageUser = messageUser
        this.messageText = messageText
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