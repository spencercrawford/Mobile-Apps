package com.example.finalproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AlertDialog
import android.text.format.DateFormat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import java.lang.Error
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class RegisterActivity : AppCompatActivity() {

    var firebaseAuth = FirebaseAuth.getInstance()
    lateinit var registerButton: Button
    lateinit var alreadyMemberButton: Button
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var sharedPreferences: SharedPreferences
    val PREF_FILENAME = "com.example.finalproject"
    val firebaseDatabase = FirebaseDatabase.getInstance()
    lateinit var user: FirebaseUser
    var dbReference = firebaseDatabase.reference
    val TAG = "NATURAL"

    lateinit var username: String

    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var genderIdenity: String
    var age = -1
    lateinit var sexualOrientation: String
    lateinit var educationLevel: String
    lateinit var school: String
    lateinit var city: String
    lateinit var state: String
    lateinit var referredBy: String
    lateinit var phoneNumber: String
    lateinit var email: String

    lateinit var firstNameEditText: EditText
    lateinit var lastNameEditText: EditText

    lateinit var genderIdentityRadioGroup: RadioGroup
    lateinit var maleRadioButton: RadioButton
    lateinit var femaleRadioButton: RadioButton
    lateinit var genderNeutralRadioButton: RadioButton
    lateinit var genderPreferNotRadioButton: RadioButton

    lateinit var ageEditText: EditText

    lateinit var sexualOrientationRadioGroup: RadioGroup
    lateinit var straightRadioButton: RadioButton
    lateinit var gayRadioButton: RadioButton
    lateinit var bisexualRadioButton: RadioButton
    lateinit var asexualRadioButton: RadioButton
    lateinit var sexualPreferNotRadioButton: RadioButton

    lateinit var asianCheckBox: CheckBox
    var asian = false
    lateinit var blackAfricanCheckBox: CheckBox
    var blackAfrican = false
    lateinit var whiteCaucasianCheckBox: CheckBox
    var whiteCaucasian = false
    lateinit var hispanicLatinXCheckBox: CheckBox
    var hispanicLatinx = false
    lateinit var nativeAmericanCheckBox: CheckBox
    var nativeAmerican = false
    lateinit var pacificIslanderCheckBox: CheckBox
    var pacificIslander = false
    lateinit var ethnicityPreferNotCheckBox: CheckBox
    var ethnicityPreferNot = false
    lateinit var ethnicityOtherCheckBox: CheckBox
    var ethnicityOther = false

    lateinit var ethnicityArrayList: ArrayList<String>

    lateinit var educationLevelRadioGroup: RadioGroup
    lateinit var elementarySchoolRadioButton: RadioButton
    lateinit var middleSchoolRadioButton: RadioButton
    lateinit var highSchoolRadioButton: RadioButton
    lateinit var higherEducationRadioButton: RadioButton
    lateinit var homeSchoolRadioButton: RadioButton
    lateinit var educationPreferNotRadioButton: RadioButton

    lateinit var schoolsAutoCompleteTextView: AutoCompleteTextView
    lateinit var citiesAutoCompleteTextView: AutoCompleteTextView
    lateinit var statesAutoCompleteTextView: AutoCompleteTextView

    lateinit var referredByRadioGroup: RadioGroup
    lateinit var selfRadioButton: RadioButton
    lateinit var friendRadioButton: RadioButton
    lateinit var parentGuardianRadioButton: RadioButton
    lateinit var schoolReferredRadioButton: RadioButton
    lateinit var courtSystemRadioButton: RadioButton

    lateinit var phoneNumberEditText: EditText
    lateinit var emailEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        toolbar = this.findViewById(R.id.registerToolbar)
        setSupportActionBar(toolbar)

        if (firebaseAuth.currentUser != null) {
            firebaseAuth.signOut()
            AuthUI.getInstance().signOut(applicationContext)
        }

        registerButton = findViewById(R.id.registerButton)
        alreadyMemberButton = findViewById(R.id.alreadyMemberButton)

        firstNameEditText = findViewById(R.id.registerFirstNameEditText)
        lastNameEditText = findViewById(R.id.registerLastNameEditText)

        genderIdentityRadioGroup = findViewById(R.id.registerGenderIdRadioGroup)
        maleRadioButton = findViewById(R.id.registerMaleRadioButton)
        femaleRadioButton = findViewById(R.id.registerFemaleRadioButton)
        genderNeutralRadioButton = findViewById(R.id.registerGenderNeutralRadioButton)
        genderPreferNotRadioButton = findViewById(R.id.registerPreferNotGenderRadioButton)

        ageEditText = findViewById(R.id.registerAgeEditText)

        sexualOrientationRadioGroup = findViewById(R.id.registerSexualOrientationRadioGroup)
        straightRadioButton = findViewById(R.id.registerStraightRadioButton)
        gayRadioButton = findViewById(R.id.registerGayRadioButton)
        bisexualRadioButton = findViewById(R.id.registerBisexualRadioButton)
        asexualRadioButton = findViewById(R.id.registerAsexualRadioButton)
        sexualPreferNotRadioButton = findViewById(R.id.registerPreferNotOrientationRadioButton)

        asianCheckBox = findViewById(R.id.registerAsianCheckBox)
        blackAfricanCheckBox = findViewById(R.id.registerBlackAfricanCheckBox)
        whiteCaucasianCheckBox = findViewById(R.id.registerWhiteCaucasianCheckBox)
        hispanicLatinXCheckBox = findViewById(R.id.registerHispanicLatinXCheckBox)
        nativeAmericanCheckBox = findViewById(R.id.registerNativeAmericanCheckBox)
        pacificIslanderCheckBox = findViewById(R.id.registerPacificIslanderCheckBox)
        ethnicityPreferNotCheckBox = findViewById(R.id.registerPreferNotEthnicityCheckBox)
        ethnicityOtherCheckBox = findViewById(R.id.registerOtherEthnicityCheckBox)

        ethnicityArrayList = ArrayList(0)

        educationLevelRadioGroup = findViewById(R.id.registerEducationLevelRadioGroup)
        elementarySchoolRadioButton = findViewById(R.id.registerElementarySchoolRadioButton)
        middleSchoolRadioButton = findViewById(R.id.registerMiddleSchoolRadioButton)
        highSchoolRadioButton = findViewById(R.id.registerHighSchoolRadioButton)
        higherEducationRadioButton = findViewById(R.id.registerHigherEducationRadioButton)
        homeSchoolRadioButton = findViewById(R.id.registerHomeSchoolRadioButton)
        educationPreferNotRadioButton = findViewById(R.id.registerPreferNotEducationRadioButton)

        schoolsAutoCompleteTextView = findViewById(R.id.registerSchoolsAutoComplete)
        statesAutoCompleteTextView = findViewById(R.id.registerStatesAutoComplete)
        citiesAutoCompleteTextView = findViewById(R.id.registerCitiesAutoComplete)

        val schoolsArray: Array<String> = resources.getStringArray(R.array.schools_array)
        val citiesArray: Array<String> = resources.getStringArray(R.array.cities_array)
        val statesArray: Array<String> = resources.getStringArray(R.array.states_array)

        ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, schoolsArray).also { adapter ->
            schoolsAutoCompleteTextView.setAdapter(adapter)
            adapter.setNotifyOnChange(true)
        }

        ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, citiesArray).also { adapter ->
            citiesAutoCompleteTextView.setAdapter(adapter)
            adapter.setNotifyOnChange(true)
        }

        ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, statesArray).also { adapter ->
            statesAutoCompleteTextView.setAdapter(adapter)
            adapter.setNotifyOnChange(true)
        }

        referredByRadioGroup = findViewById(R.id.registerReferredByRadioGroup)
        selfRadioButton = findViewById(R.id.registerSelfRadioButton)
        friendRadioButton = findViewById(R.id.registerFriendRadioButton)
        parentGuardianRadioButton = findViewById(R.id.registerParentGuardianRadioButton)
        schoolReferredRadioButton = findViewById(R.id.registerSchoolReferredRadioButton)
        courtSystemRadioButton = findViewById(R.id.registerCourtSystemRadioButton)

        phoneNumberEditText = findViewById(R.id.registerPhoneNumberEditText)
        emailEditText = findViewById(R.id.registerEmailEditText)

        sharedPreferences = getSharedPreferences(PREF_FILENAME, 0)

        registerButton.setOnClickListener {

            firstName = firstNameEditText.text.toString()
            lastName = lastNameEditText.text.toString()

            var selectedGenderIdentityInt: Int = genderIdentityRadioGroup.checkedRadioButtonId
            Log.i(TAG, "Gender: $selectedGenderIdentityInt")
            genderIdenity = if (selectedGenderIdentityInt == -1) {
                ""
            } else {
                var selectedGenderIdentityRadioButton: RadioButton = findViewById(selectedGenderIdentityInt)
                selectedGenderIdentityRadioButton.text.toString()
            }

            age = if (ageEditText.text.isEmpty()) {
                -1
            } else {
                Integer.parseInt(ageEditText.text.toString())
            }

            var selectedSexualOrientationInt: Int = sexualOrientationRadioGroup.checkedRadioButtonId
            Log.i(TAG, "Sexual: $selectedSexualOrientationInt")
            sexualOrientation = if (selectedSexualOrientationInt == -1) {
                ""
            } else {
                var selectedSexualOrientationRadioButton: RadioButton = findViewById(selectedSexualOrientationInt)
                selectedSexualOrientationRadioButton.text.toString()
            }

            asian = asianCheckBox.isChecked
            blackAfrican = blackAfricanCheckBox.isChecked
            whiteCaucasian = whiteCaucasianCheckBox.isChecked
            hispanicLatinx = hispanicLatinXCheckBox.isChecked
            nativeAmerican = nativeAmericanCheckBox.isChecked
            pacificIslander = pacificIslanderCheckBox.isChecked
            ethnicityPreferNot = ethnicityPreferNotCheckBox.isChecked
            ethnicityOther = ethnicityOtherCheckBox.isChecked

            var selectedEducationLevelInt: Int = educationLevelRadioGroup.checkedRadioButtonId
            Log.i(TAG, "Education: $selectedEducationLevelInt")
            educationLevel = if (selectedEducationLevelInt == -1) {
                ""
            } else {
                var selectedEducationLevelRadioButton: RadioButton = findViewById(selectedEducationLevelInt)
                selectedEducationLevelRadioButton.text.toString()
            }

            school = schoolsAutoCompleteTextView.text.toString()
            city = citiesAutoCompleteTextView.text.toString()
            state = statesAutoCompleteTextView.text.toString()

            var selectedReferredByInt: Int = referredByRadioGroup.checkedRadioButtonId
            Log.i(TAG, "Referred: $selectedReferredByInt")
            referredBy = if (selectedReferredByInt == -1) {
                ""
            } else {
                var selectedReferredByRadioButton: RadioButton = findViewById(selectedReferredByInt)
                selectedReferredByRadioButton.text.toString()
            }

            phoneNumber = phoneNumberEditText.text.toString()

            email = emailEditText.text.toString()

            if (TextUtils.isEmpty(firstName)) {
                Toast.makeText(applicationContext, "Please Enter Your First Name.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(lastName)) {
                Toast.makeText(applicationContext, "Please Enter Your Last Name.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            username = "$firstName $lastName"

            if (selectedGenderIdentityInt == -1) {
                Toast.makeText(applicationContext, "Please Enter Your Gender Identity.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (age == -1) {
                Toast.makeText(applicationContext, "Please Enter Your Age.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (selectedSexualOrientationInt == -1) {
                Toast.makeText(applicationContext, "Please Enter Your Sexual Orientation.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (!asianCheckBox.isChecked
                && !blackAfricanCheckBox.isChecked
                && !whiteCaucasianCheckBox.isChecked
                && !hispanicLatinXCheckBox.isChecked
                && !nativeAmericanCheckBox.isChecked
                && !pacificIslanderCheckBox.isChecked
                && !ethnicityOtherCheckBox.isChecked
                && !ethnicityPreferNotCheckBox.isChecked) {
                Toast.makeText(applicationContext, "Please Enter Your Ethnicity(s).", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (selectedEducationLevelInt == -1) {
                Toast.makeText(applicationContext, "Please Enter Your Education Level.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (school.isEmpty()) {
                Toast.makeText(applicationContext, "Please Enter Your School.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (city.isEmpty()) {
                Toast.makeText(applicationContext, "Please Enter Your City.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (state.isEmpty()) {
                Toast.makeText(applicationContext, "Please Enter Your State.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (selectedReferredByInt == -1) {
                Toast.makeText(applicationContext, "Please Enter Who Referred You.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(phoneNumber)) {
                Toast.makeText(applicationContext, "Please Enter Your Phone Number.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (phoneNumber.length < 10) {
                Toast.makeText(applicationContext, "Please Enter A Valid Phone Number.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(applicationContext, "Please Enter Your Email.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            firebaseAuth.createUserWithEmailAndPassword(email, phoneNumber)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user = firebaseAuth.currentUser!!
                        var profileUpdates: UserProfileChangeRequest = UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build()
                        user.updateProfile(profileUpdates).addOnCompleteListener {
                            if(it.isSuccessful) {
                                Log.i(TAG, "Profile Updated!")
                            }
                            if(it.isCanceled) {
                                Log.i(TAG, "Profile Not Updated!")
                            }
                        }

                        val time = Date().time
                        val startDate = DateFormat.format("MM-dd-yyyy", time).toString()

                        if (asianCheckBox.isChecked)
                            ethnicityArrayList.add(asianCheckBox.text.toString())

                        if (whiteCaucasianCheckBox.isChecked)
                            ethnicityArrayList.add(whiteCaucasianCheckBox.text.toString())

                        if (blackAfricanCheckBox.isChecked)
                            ethnicityArrayList.add(blackAfricanCheckBox.text.toString())

                        if (hispanicLatinXCheckBox.isChecked)
                            ethnicityArrayList.add(hispanicLatinXCheckBox.text.toString())

                        if (nativeAmericanCheckBox.isChecked)
                            ethnicityArrayList.add(nativeAmericanCheckBox.text.toString())

                        if (pacificIslanderCheckBox.isChecked)
                            ethnicityArrayList.add(pacificIslanderCheckBox.text.toString())

                        if (ethnicityOtherCheckBox.isChecked)
                            ethnicityArrayList.add(ethnicityOtherCheckBox.text.toString())

                        if (ethnicityPreferNotCheckBox.isChecked) {
                            ethnicityArrayList.clear()
                            ethnicityArrayList.add(ethnicityPreferNotCheckBox.text.toString())
                        }

                        val newUser = ChatUser(username, startDate, firstName, lastName, genderIdenity, age, sexualOrientation, ethnicityArrayList, educationLevel, school, city, state, email, phoneNumber)
                        dbReference.child("users").push().setValue(newUser)
                        Toast.makeText(applicationContext, "Account Created!", Toast.LENGTH_SHORT).show()
                        FirebaseAuth.getInstance().signOut()
                        AuthUI.getInstance().signOut(applicationContext)
                        startActivity(Intent(applicationContext, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Failed: ${task.exception!!.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        alreadyMemberButton.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
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
}