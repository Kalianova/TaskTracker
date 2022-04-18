package ru.kalianova.rpgtracker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.snappydb.DBFactory

import com.snappydb.DB

import com.snappydb.SnappydbException




class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var auth: FirebaseAuth
    private var firebaseUser: FirebaseUser? = null
    private lateinit var buttonRegister: Button
    private lateinit var sharedPreference: SharedPreferences
    private val SETTINGS_NAME: String = "profile_settings"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreference = getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE)

        editTextEmail = findViewById(R.id.editTextLoginEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonRegister = findViewById(R.id.buttonRegisterLogin)

        buttonRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser

        if(firebaseUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun clickLogin(view: View) {
        var emailText: String = editTextEmail.text.trim().toString()
        var passwordText: String = editTextPassword.text.trim().toString()

        if (emailText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(this, "Поля не заполнены", Toast.LENGTH_SHORT).show()
        } else {
            auth.signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        sharedPreference.edit().putString("Login", "Name").apply()
                        val intent: Intent = Intent(this, MainActivity::class.java);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Войти не получилось", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            putValues(emailText, "")
        }
    }

    @Throws(SnappydbException::class)
    private fun putValues(email: String, login: String) {
        val snappyDB = DBFactory.open(this, "User")
        snappyDB.put("Email", email)
        snappyDB.put("Login", login)
    }
}