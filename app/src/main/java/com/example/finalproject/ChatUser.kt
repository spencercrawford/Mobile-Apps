package com.example.finalproject

class ChatUser {

    private var name: String = ""
    private var startDate: String = ""

    constructor()

    constructor(name: String, startDate: String) {
        this.name = name
        this.startDate = startDate
    }

    fun getName():String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getStartDate():String {
        return startDate
    }

    fun setStartDate(startDate: String) {
        this.startDate = startDate
    }
}