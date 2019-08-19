package com.example.finalproject

class ChatUser {

    private var name: String = ""
    private var startDate: String = ""
    private var firstName: String = ""
    private var lastName: String = ""
    private var genderIdentity: String = ""
    private var age: Int = 0
    private var sexualOrientation: String = ""
    private var ethnicity: ArrayList<String> = ArrayList(0)
    private var educationLevel: String = ""
    private var school: String = ""
    private var city: String = ""
    private var state: String = ""
    private var email: String = ""
    private var phoneNumber: String = ""

    constructor()

    constructor(name: String,
                startDate: String,
                firstName: String,
                lastName: String,
                genderIdentity: String,
                age: Int,
                sexualOrientation: String,
                ethnicity: ArrayList<String>,
                educationLevel: String,
                school: String,
                city: String,
                state: String,
                email: String,
                phoneNumber: String) {
        this.name = name
        this.startDate = startDate
        this.firstName = firstName
        this.lastName = lastName
        this.genderIdentity = genderIdentity
        this.age = age
        this.sexualOrientation = sexualOrientation
        this.ethnicity = ethnicity
        this.educationLevel = educationLevel
        this.school = school
        this.city = city
        this.state = state
        this.email = email
        this.phoneNumber = phoneNumber
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

    fun getFirstName():String {
        return firstName
    }

    fun setFirstName(firstName: String) {
        this.firstName = firstName
    }

    fun getLastName():String {
        return lastName
    }

    fun setLastName(lastName: String) {
        this.lastName = lastName
    }

    fun getGenderIdentity():String {
        return genderIdentity
    }

    fun setGenderIdentity(genderIdentity: String) {
        this.genderIdentity = genderIdentity
    }

    fun getAge():Int {
        return age
    }

    fun setAge(age: Int) {
        this.age = age
    }

    fun getSexualOrientation():String {
        return sexualOrientation
    }

    fun setSexualOrientation(sexualOrientation: String) {
        this.sexualOrientation = sexualOrientation
    }

    fun getEthnicity():ArrayList<String> {
        return ethnicity
    }

    fun setEthnicity(ethnicity: ArrayList<String>) {
        this.ethnicity = ethnicity
    }

    fun getEducationLevel():String {
        return educationLevel
    }

    fun setEcucationLevel(educationLevel: String) {
        this.educationLevel = educationLevel
    }

    fun getSchool():String {
        return school
    }

    fun setSchool(school: String) {
        this.school = school
    }

    fun getCity():String {
        return city
    }

    fun setCity(city: String) {
        this.city = city
    }

    fun getState():String {
        return state
    }

    fun setState(state: String) {
        this.state = state
    }

    fun getEmail():String {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getPhoneNumber():String {
        return phoneNumber
    }

    fun setPhoneNumber(phoneNumber: String) {
        this.phoneNumber = phoneNumber
    }
}